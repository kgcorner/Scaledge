package com.kgcorner.scaledgedata.dao;


import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Contract for scaledge repository
 * @param <T> @{@link Serializable}
 */
@Repository
public interface ScaledgeRepository<T extends Serializable> {
    /**
     * Get documents available in database
     * @param type type of the model
     * @return list of documents
     */
    List<T> getAll(Class<T> type);

    /**
     * Get at most given number of documents
     * @param maxCount max number of documents to be fetched
     * @param type type of the model
     * @return list of documents
     */
    List<T> getAll(int maxCount, Class<T> type);

    /**
     *  Get at most given number of documents from given offset
     * @param page page number
     * @param itemsCount number of items in page
     * @param type type of the model
     * @return list of documents
     */
    List<T> getAll(int page, int itemsCount, Class<T> type);

    /**
     * Get document identified by given id
     * @param id
     * @param type type of the model
     * @return
     */
    T getById(String id, Class<T> type);

    /**
     * Get document identified by given id
     * @param key key to check
     * @param value key to check
     * @param type type of the model
     * @return found document
     */
    T getByKey(String key, String value, Class<T> type);

    /**
     * Creates and return document
     * @param document
     * @return saved document
     */
    T create(T document);

    /**
     * Updates given document
     * @param document
     * @return returns updated document
     */
    T update(T document);


}
