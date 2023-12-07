package com.coherent.hotel.service;

/**
 * Very basic security service
 */
public interface SecurityService {

    boolean loginUser(String username, String password);
}
