package org.debugroom.sample.aws.xray.domain.service;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.debugroom.sample.aws.xray.common.model.UserResource;
import org.debugroom.sample.aws.xray.domain.repository.UserResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@XRayEnabled
@Service
public class OchestrationServiceImpl implements OrchestrationService{

    @Autowired
    UserResourceRepository userResourceRepository;

    @Override
    public List<UserResource> getUsers() {
        return userResourceRepository.findAll();
    }

}
