package org.debugroom.sample.aws.xray.backend.service.domain.service;

import org.debugroom.sample.aws.xray.backend.service.domain.model.dynamodb.Log;

public interface AuditLogService {

    public void addLogService(Log log);

}
