package org.debugroom.sample.aws.xray.backend.service.domain.model.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@DynamoDBTable(tableName = "log-table")
public class Log {

    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LogKey logKey;

    private String userId;
    private String createdAt;

    @DynamoDBAttribute
    private String traceId;

    @DynamoDBHashKey
    public String getUserId() {
        return userId;
    }

    @DynamoDBRangeKey
    public String getCreatedAt() {
        return createdAt;
    }

}
