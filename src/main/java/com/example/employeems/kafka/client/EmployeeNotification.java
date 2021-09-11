package com.example.employeems.kafka.client;

import com.example.employeems.Employee;
import com.example.employeems.EmployeeDTO;
import com.example.employeems.kafka.client.interfaces.IEmployeeNotification;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
public class EmployeeNotification implements IEmployeeNotification {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeNotification.class);
    private static final String BOOT_STRAP_SERVER = "localhost:9092";
    private static final String TOPIC = "employee";
    private ObjectMapper obj = new ObjectMapper();
    {
        obj.registerModule(new JavaTimeModule());
        obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public void publish(List<EmployeeDTO> employeeList){
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Create Producer
        Producer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        for(EmployeeDTO employee : employeeList) {
            //Create Producer Records
            String message = writeObjToString(employee);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC,  message);

            //send data
            CallBackImp callback = new CallBackImp();
            kafkaProducer.send(producerRecord, callback);
        }
        kafkaProducer.flush();
        kafkaProducer.close();
    }
    private static class CallBackImp implements Callback {

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e == null) {
                LOGGER.info("Receive new Metadata ", recordMetadata.topic() +
                        " Offset: " + recordMetadata.offset() +
                        " time: " + recordMetadata.timestamp());
            } else {
                LOGGER.error("Error");
            }

        }
    }

    private String writeObjToString(EmployeeDTO employe){
        String jsonStr = "";
        try {
            jsonStr = obj.writeValueAsString(employe);
            LOGGER.debug("convert to obj to string {}", jsonStr);
        }
        catch (IOException e) {
            LOGGER.error("Error in parsing");
        }
        return jsonStr;
    }
}
