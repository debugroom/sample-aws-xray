package org.debugroom.sample.aws.xray.backend.service.config;

import com.amazonaws.xray.AWSXRay;
import org.debugroom.sample.aws.xray.common.apinfra.cloud.aws.CloudFormationStackResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        AWSXRay.beginSegment("backend-service-init");
        SpringApplication.run(App.class, args);
    }

}
