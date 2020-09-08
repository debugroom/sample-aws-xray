package org.debugroom.sample.aws.xray.backend.service.domain.repository.jpa;

import org.debugroom.sample.aws.xray.backend.service.domain.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
