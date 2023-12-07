package com.coherent.hotel.service.impl;

import com.coherent.hotel.configuration.SecurityCoherentConfigs;
import com.coherent.hotel.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SecurityCoherentConfigs securityCoherentConfigs;

    @Override
    public boolean loginUser(String username, String password) {
        return securityCoherentConfigs.getPassword().equals(password) &&
                securityCoherentConfigs.getUsername().equals(username);
    }
}
