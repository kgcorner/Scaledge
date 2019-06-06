package com.kgcorner.scaledgeauth.services;

import org.springframework.stereotype.Service;

/**
 * Implementation of cache service using Redis DB
 */
@Service
public class RedisCacheService implements CacheService {
    @Override
    public boolean store(String id, Object object) {
        return false;
    }

    @Override
    public Object fetch(String id) {
        return null;
    }
}
