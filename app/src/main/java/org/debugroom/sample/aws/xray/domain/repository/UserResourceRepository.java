package org.debugroom.sample.aws.xray.domain.repository;

import org.debugroom.sample.aws.xray.common.model.UserResource;

import java.util.List;

public interface UserResourceRepository {

    public UserResource findOne(Long id);
    public List<UserResource> findAll();

}
