package ru.innopolis.smoldyrev.common.support;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @SpringCecurity
 * Spring in Action 4
 * 381p
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MethodSecurityConfig
        extends GlobalMethodSecurityConfiguration {

}

