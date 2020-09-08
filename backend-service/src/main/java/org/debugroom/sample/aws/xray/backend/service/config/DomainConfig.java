package org.debugroom.sample.aws.xray.backend.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.debugroom.sample.aws.xray.backend.service.domain.service")
public class DomainConfig {
}
