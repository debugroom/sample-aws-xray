package org.debugroom.sample.aws.xray.domain.repository;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.debugroom.sample.aws.xray.common.model.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@XRayEnabled
@Component
public class UserResourceRepositoryImpl implements UserResourceRepository{

    private static final String SERVICE_NAME = "/backend/user";
    private static final String API_VERSION = "/api/v1";

    @Autowired
    WebClient webClient;

    @Override
    public UserResource findOne(Long id) {
        String endpoint = SERVICE_NAME + API_VERSION + "/users/{userId}";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(endpoint).build(id))
                .retrieve()
                .bodyToMono(UserResource.class)
                .block();
    }

    @Override
    public List<UserResource> findAll() {
        String endpoint = SERVICE_NAME + API_VERSION + "/users";
        return Arrays.asList(
                webClient.get().uri(
                        uriBuilder -> uriBuilder.path(endpoint).build())
                .retrieve()
                .bodyToMono(UserResource[].class).block());
    }

}
