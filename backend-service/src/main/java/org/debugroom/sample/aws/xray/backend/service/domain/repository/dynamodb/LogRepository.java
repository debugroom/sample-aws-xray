package org.debugroom.sample.aws.xray.backend.service.domain.repository.dynamodb;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import org.debugroom.sample.aws.xray.backend.service.domain.model.dynamodb.Log;
import org.debugroom.sample.aws.xray.backend.service.domain.model.dynamodb.LogKey;

@EnableScan
public interface LogRepository extends CrudRepository<Log, LogKey> {
}
