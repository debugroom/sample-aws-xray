package org.debugroom.sample.aws.xray.backend.service.config;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.sql.TracingDataSource;
import org.debugroom.sample.aws.xray.common.apinfra.cloud.aws.CloudFormationStackResolver;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RdsConfig {

    private static final String dbName = "debugroom-sample-aws-xray-vpc-RDS-DBName";
    private static final String rdsEndpoint = "debugroom-sample-aws-xray-vpc-RDS-Endpoint";
    private static final String rdsUserName = "debugroom-sample-aws-xray-vpc-RDS-UserName";

    @Bean
    CloudFormationStackResolver cloudFormationStackResolver(){
        return new CloudFormationStackResolver();
    }

    @Bean
    AWSSimpleSystemsManagement awsSimpleSystemsManagement(){
        return AWSSimpleSystemsManagementClientBuilder.defaultClient();
    }

    @Bean
    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://" +
                cloudFormationStackResolver().getExportValue(rdsEndpoint)
                + ":5432/sample_database");
        dataSourceBuilder.username(cloudFormationStackResolver().getExportValue(rdsUserName));
        GetParameterRequest request = new GetParameterRequest();
        request.setName("sample-xray-rds-master-password");
        request.setWithDecryption(true);
        dataSourceBuilder.password(awsSimpleSystemsManagement().getParameter(request)
                .getParameter().getValue());
        DataSource dataSource = dataSourceBuilder.build();
        return new TracingDataSource(dataSource);
    }

}
