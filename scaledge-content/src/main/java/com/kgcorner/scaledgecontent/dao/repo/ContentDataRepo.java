package com.kgcorner.scaledgecontent.dao.repo;

/*
Description : <Write class Description>
Author: kumar
Created on : 4/6/19
*/


import com.kgcorner.scaledgedata.dao.ScaledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.kgcorner.scaledgecontent.dao.model.Content;

import java.io.Serializable;
import java.util.List;

@Repository
public class ContentDataRepo implements ScaledgeRepository<Content> {
    @Autowired
    private MongoTemplate template;

    /**
     * @see ScaledgeRepository#getAll(Class)
     */
    @Override
    public List<Content> getAll(Class<Content> type) {
        return template.findAll(type);
    }

    /**
     * @see ScaledgeRepository#getAll(int, Class)
     */
    @Override
    public List getAll(int maxCount, Class<Content> type) {
        return getAll(0, maxCount, type);
    }

    /**
     * @see ScaledgeRepository#getAll(int, int, Class)
     * @param page
     * @param itemsCount
     */
    @Override
    public List getAll(int page, int itemsCount, Class<Content> type) {
        Pageable pageable = PageRequest.of(page, itemsCount);
        Query query = new Query().with(pageable);
        return template.find(query, type);
    }

    /**
     * @see ScaledgeRepository#getById(String, Class)
     */
    @Override
    public Content getById(String id, Class<Content> type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return template.findOne(query, type);
    }

    /**
     * @see ScaledgeRepository#getByKey(String, String, Class)
     */
    @Override
    public Content getByKey(String key, String value, Class<Content> type) {
        Query query = new Query();
        query.addCriteria(Criteria.where(key).is(value));
        return template.findOne(query, type);
    }

    /**
     * @see ScaledgeRepository#create(Serializable)
     */
    @Override
    public Content create(Content document) {
        template.insert(document);
        return  document;
    }

    @Override
    public Content update(Content document) {
        template.save(document);
        return document;
    }
}