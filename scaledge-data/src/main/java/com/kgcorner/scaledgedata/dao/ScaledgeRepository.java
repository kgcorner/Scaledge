package com.kgcorner.scaledgedata.dao;


import java.io.Serializable;
import java.util.List;

/**
 * Contract for scaledge repository
 * @param <T> @{@link Serializable}
 */
public interface ScaledgeRepository<T extends Serializable> {
    /**
     * Get documents available in database
     * @return list of documents
     */
    List<T> getAll();

    /**
     * Get at most given number of documents
     * @param maxCount max number of documents to be fetched
     * @return list of documents
     */
    List<T> getAll(int maxCount);

    /**
     *  Get at most given number of documents from given offset
     * @param page page number
     * @param itemsCount number of items in page
     * @return list of documents
     */
    List<T> getAll(int page, int itemsCount);

    /**
     * Get document identified by given id
     * @param id
     * @return
     */
    T getById(String id);

    /**
     * Get document identified by given id
     * @param key key to check
     * @param value key to check
     *
     * @return found document
     */
    T getByKey(String key, String value);

    /**
     * Creates and return document
     * @param document
     * @return saved document
     */
    T create(T document);


}
