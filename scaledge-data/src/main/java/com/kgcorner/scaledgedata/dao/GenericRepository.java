package com.kgcorner.scaledgedata.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


@Repository
public class GenericRepository<T extends Serializable> implements ScaledgeRepository<T> {

    @Autowired
    private MongoTemplate template;

    private final Class<T> documentType;

    public GenericRepository() {
        this.documentType = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    /**
     * @see ScaledgeRepository#getAll()
     */
    @Override
    public List getAll() {
        return template.findAll(documentType);
    }

    /**
     * @see ScaledgeRepository#getAll(int)
     */
    @Override
    public List getAll(int maxCount) {
        return getAll(0, maxCount);
    }

    /**
     * @see ScaledgeRepository#getAll(int, int)
     * @param page
     * @param itemsCount
     */
    @Override
    public List getAll(int page, int itemsCount) {
        Pageable pageable = PageRequest.of(page, itemsCount);
        Query query = new Query().with(pageable);
        return template.find(query, documentType);
    }

    /**
     * @see ScaledgeRepository#getById(String)
     */
    @Override
    public T getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return template.findOne(query, documentType);
    }

    /**
     * @see ScaledgeRepository#getByKey(String, String)
     */
    @Override
    public T getByKey(String key, String value) {
        Query query = new Query();
        query.addCriteria(Criteria.where(key).is(value));
        return template.findOne(query, documentType);
    }

    /**
     * @see ScaledgeRepository#create(Serializable)
     */
    @Override
    public T create(T document) {
        template.save(document);
        return  document;
    }
}
