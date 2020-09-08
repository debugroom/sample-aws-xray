package org.debugroom.sample.aws.xray.config;

import com.amazonaws.xray.AWSXRay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        AWSXRay.beginSegment("app-init");
        SpringApplication.run(App.class, args);
    }

}
