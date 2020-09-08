package org.debugroom.sample.aws.xray.backend.service.app.model;

import org.debugroom.sample.aws.xray.backend.service.domain.model.jpa.User;
import org.debugroom.sample.aws.xray.common.model.UserResource;

import java.util.List;
import java.util.stream.Collectors;

public interface UserResourceMapper {

    public static UserResource map(User user){
        return UserResource.builder()
                .id(user.getUserId())
                .firstName(user.getFirstName())
                .familyName(user.getFamilyName())
                .loginId(user.getLoginId())
                .isLogin(user.getIsLogin())
                .build();
    }

    public static List<UserResource> map(List<User> users){
        return users.stream().map(UserResourceMapper::map)
                .collect(Collectors.toList());
    }

}
