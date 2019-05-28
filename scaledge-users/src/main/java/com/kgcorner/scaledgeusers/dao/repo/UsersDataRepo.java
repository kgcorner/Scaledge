package com.kgcorner.scaledgeusers.dao.repo;

/*
Description : <Write is class Description>
Author: kumar
Created on : 27/5/19
*/

import com.kgcorner.scaledgedata.dao.ScaledgeRepository;
import com.kgcorner.scaledgeusers.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;

public class UsersDataRepo implements ScaledgeRepository<User> {
    
    @Autowired
    private MongoTemplate template;

    /**
     * @see ScaledgeRepository#getAll(Class)
     */
    @Override
    public List<User> getAll(Class<User> type) {
        return template.findAll(type);
    }

    /**
     * @see ScaledgeRepository#getAll(int, Class)
     */
    @Override
    public List getAll(int maxCount, Class<User> type) {
        return getAll(0, maxCount, type);
    }

    /**
     * @see ScaledgeRepository#getAll(int, int, Class)
     * @param page
     * @param itemsCount
     */
    @Override
    public List getAll(int page, int itemsCount, Class<User> type) {
        Pageable pageable = PageRequest.of(page, itemsCount);
        Query query = new Query().with(pageable);
        return template.find(query, type);
    }

    /**
     * @see ScaledgeRepository#getById(String, Class)
     */
    @Override
    public User getById(String id, Class<User> type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return template.findOne(query, type);
    }

    /**
     * @see ScaledgeRepository#getByKey(String, String, Class)
     */
    @Override
    public User getByKey(String key, String value, Class<User> type) {
        Query query = new Query();
        query.addCriteria(Criteria.where(key).is(value));
        return template.findOne(query, type);
    }

    /**
     * @see ScaledgeRepository#create(Serializable)
     */
    @Override
    public User create(User document) {
        template.insert(document);
        return  document;
    }

    @Override
    public User update(User document) {
        template.save(document);
        return document;
    }
}