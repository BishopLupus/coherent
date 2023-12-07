package com.coherent.hotel.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Simple properties model representation class
 */
@Configuration
@ConfigurationProperties(prefix = "coherent.security")
@Data
public class SecurityCoherentConfigs {

    private String username;
    private String password;
}
