package org.debugroom.sample.aws.xray.backend.service.domain.service;

import org.debugroom.sample.aws.xray.backend.service.domain.model.jpa.User;

import java.util.List;

public interface UserService {

    public List<User> getUsers();

}
