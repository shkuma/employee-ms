package com.example.employeems.cb.repository.connections;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CBConnection  extends AbstractCouchbaseConfiguration {
    private static final String BOOT_STRAP_SERVER = "localhost";
    private static final String USER_NAME = "Administrator";
    private static final String PASSWORD = "Administrator";
    private static final String BUCKET_NAME = "com.employee";
    @Override
    public String getConnectionString() {
        return BOOT_STRAP_SERVER;
    }

    @Override
    public String getUserName() {
        return USER_NAME;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }

}
