package org.debugroom.sample.aws.xray.backend.service.app.web;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.debugroom.sample.aws.xray.backend.service.app.model.UserResourceMapper;
import org.debugroom.sample.aws.xray.backend.service.domain.model.dynamodb.Log;
import org.debugroom.sample.aws.xray.backend.service.domain.service.AuditLogService;
import org.debugroom.sample.aws.xray.backend.service.domain.service.UserService;
import org.debugroom.sample.aws.xray.common.model.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@XRayEnabled
@RestController
@RequestMapping("api/v1")
public class SampleController {


    @Autowired
    UserService userService;

    @Autowired
    AuditLogService auditLogService;

    @GetMapping("/users")
    public List<UserResource> getUsers(){
        return UserResourceMapper.map(userService.getUsers());
    }

    @PostMapping("/log")
    public Log addLogController(@RequestParam("userId")String userId){
        Log log = Log.builder()
                .userId(userId)
                .traceId(UUID.randomUUID().toString())
                .build();

        auditLogService.addLogService(log);

        return log;
    }

}
