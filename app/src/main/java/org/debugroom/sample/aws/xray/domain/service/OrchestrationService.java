package org.debugroom.sample.aws.xray.domain.service;

import org.debugroom.sample.aws.xray.common.model.UserResource;

import java.util.List;

public interface OrchestrationService {

    public List<UserResource> getUsers();

}
