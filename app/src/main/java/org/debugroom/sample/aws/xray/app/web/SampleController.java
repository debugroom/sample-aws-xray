package org.debugroom.sample.aws.xray.app.web;

import java.util.Arrays;
import java.util.List;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.debugroom.sample.aws.xray.common.model.UserResource;
import org.debugroom.sample.aws.xray.domain.service.OrchestrationService;

@XRayEnabled
@RestController
@RequestMapping("api/v1")
public class SampleController {

    @Autowired
    OrchestrationService orchestrationService;

    @GetMapping("/users")
    public List<UserResource> getUser(){
        return orchestrationService.getUsers();
    }

}
