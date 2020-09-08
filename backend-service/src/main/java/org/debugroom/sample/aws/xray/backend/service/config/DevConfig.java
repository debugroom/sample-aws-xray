package org.debugroom.sample.aws.xray.backend.service.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.handlers.TracingHandler;
import org.debugroom.sample.aws.xray.common.apinfra.cloud.aws.CloudFormationStackResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DevConfig {

    private static final String DYNAMODB_ENDPOINT_EXPORT = "debugroom-sample-aws-xray-vpc-DynamoDB-Dev-ServiceEndpoint";
    private static final String DYNAMODB_REGION_EXPORT = "debugroom-sample-aws-xray-vpc-DynamoDB-Dev-Region";

    @Autowired
    CloudFormationStackResolver cloudFormationStackResolver;

    @Bean
    DynamoDBMapperConfig dynamoDBMapperConfig(){
        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(
                        DynamoDBMapperConfig.TableNameOverride
                                .withTableNamePrefix("dev_"))
                .build();
    }

    @Bean
    AmazonDynamoDB amazonDynamoDB(){
        return AmazonDynamoDBAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                cloudFormationStackResolver.getExportValue(DYNAMODB_ENDPOINT_EXPORT),
                                cloudFormationStackResolver.getExportValue(DYNAMODB_REGION_EXPORT)))
                .withRequestHandlers(new TracingHandler(AWSXRay.getGlobalRecorder()))
                .build();
    }

}
