package com.kgcorner.scaledge.cache.services;

public interface CacheService {
    boolean store(String id, Object object);
    Object fetch(String id);

}
