package org.debugroom.sample.aws.xray.backend.service.domain.service;

import java.util.List;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.debugroom.sample.aws.xray.backend.service.domain.model.jpa.User;
import org.debugroom.sample.aws.xray.backend.service.domain.repository.jpa.UserRepository;

@XRayEnabled
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsers(){
        return userRepository.findAll();
    }

}
