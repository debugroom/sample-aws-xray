package org.debugroom.sample.aws.xray.backend.service.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(
        basePackages = "org.debugroom.sample.aws.xray.backend.service.domain.repository.dynamodb")
public class DynamoDBConfig {
}
