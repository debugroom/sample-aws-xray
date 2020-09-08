package org.debugroom.sample.aws.xray.backend.service.domain.service;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.debugroom.sample.aws.xray.backend.service.domain.model.dynamodb.Log;
import org.debugroom.sample.aws.xray.backend.service.domain.repository.dynamodb.LogRepository;
import org.debugroom.sample.aws.xray.common.apinfra.util.DateStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@XRayEnabled
@Service
public class AuditLogServiceImpl implements AuditLogService{

    @Autowired
    LogRepository logRepository;

    @Override
    public void addLogService(Log log) {
        log.setCreatedAt(DateStringUtil.now());
        logRepository.save(log);
    }

}
