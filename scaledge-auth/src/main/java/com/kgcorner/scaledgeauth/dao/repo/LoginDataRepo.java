package com.kgcorner.scaledgeauth.dao.repo;

/*
Description : <Write is class Description>
Author: kumar
Created on : 25/5/19
*/

import com.kgcorner.scaledgeauth.dao.entity.Login;
import com.kgcorner.scaledgeauth.services.CacheService;
import com.kgcorner.scaledgedata.dao.ScaledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class LoginDataRepo implements ScaledgeRepository<Login> {


    @Autowired
    private CacheService cacheService;

    @Autowired
    private MongoTemplate template;

    /**
     * @see ScaledgeRepository#getAll(Class)
     */
    @Override
    public List<Login> getAll(Class<Login> type) {
        return template.findAll(type);
    }

    /**
     * @see ScaledgeRepository#getAll(int, Class)
     */
    @Override
    public List getAll(int maxCount, Class<Login> type) {
        return getAll(0, maxCount, type);
    }

    /**
     * @see ScaledgeRepository#getAll(int, int, Class)
     * @param page
     * @param itemsCount
     */
    @Override
    public List getAll(int page, int itemsCount, Class<Login> type) {
        Pageable pageable = PageRequest.of(page, itemsCount);
        Query query = new Query().with(pageable);
        return template.find(query, type);
    }

    /**
     * @see ScaledgeRepository#getById(String, Class)
     */
    @Override
    public Login getById(String id, Class<Login> type) {
        Object cachedValue = cacheService.fetch(id);
        if(cachedValue != null && cachedValue instanceof Login)
            return (Login) cachedValue;
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Login login = template.findOne(query, type);
        cacheService.store(id, login);
        return login;
    }

    /**
     * @see ScaledgeRepository#getByKey(String, String, Class)
     */
    @Override
    public Login getByKey(String key, String value, Class<Login> type) {
        Query query = new Query();
        query.addCriteria(Criteria.where(key).is(value));
        return template.findOne(query, type);
    }

    /**
     * @see ScaledgeRepository#create(Serializable)
     */
    @Override
    public Login create(Login document) {

        template.insert(document);
        cacheService.store(document.getId(), document);
        return  document;
    }

    @Override
    public Login update(Login document) {
        template.save(document);
        cacheService.store(document.getId(), document);
        return document;
    }
}