package org.debugroom.sample.aws.xray.backend.service.domain.model.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LogKey implements Serializable {

    @DynamoDBHashKey
    private String userId;
    @DynamoDBRangeKey
    private String createdAt;

}
