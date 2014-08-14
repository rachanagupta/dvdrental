package com.dvd.rental.capstone.business.dao.common;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * To provide the interface for abstraction of the common logic
 * 
 * @author rachana
 * @since Aug, 10 2014
 * 
 */
public interface Dao {
    /**
     * To find the object by Id
     * 
     * @param id
     *            - caching key
     * @param T
     *            - object in request
     * @param query
     *            - query to be fired against DB
     * @param rowMapper
     *            - {@link RowMapper} to convert {@link Row} to T
     * @param cacheDuration
     *            - The amount of time the object to be stored in cache
     * @param params
     *            - The values to be inserted in the table.           
     * @return T
     * @throws ComponentException
     */
    public <T> T findById(Serializable id, Class<T> T, String query,
            RowMapper<T> rowMapper, CacheDuration cacheDuration, Object... params)
            throws ComponentException;

    /**
     * To find the multiple rows by given criteria in query
     * 
     * @param id
     *            - caching key
     * @param T
     *            - object in request
     * @param query
     *            - query to be fired against DB
     * @param rowMapper
     *            - {@link RowMapper} to convert {@link Row} to T
     * @param cacheDuration
     *            - The amount of time the object to be stored in cache
     * @param params
     *            - The values to be inserted in the table.
     * @return T
     * @throws ComponentException
     */
    public <T> List<T> find(Serializable id, Class<T> T, String query,
            RowMapper<T> rowMapper, CacheDuration cacheDuration,
            Object... params) throws ComponentException;

    /**
     * To create the row
     * 
     * @param id
     *            - caching key
     * @param T
     *            - object in request
     * @param query
     *            - query to be fired against DB
     * @param cacheDuration
     *            - The amount of time the object to be stored in cache
     * @param params
     *            - column values to be inserted
     * @throws ComponentException
     */
    public <T> void create(Serializable id, Class<T> T, String query,
            CacheDuration cacheDuration, Object... params)
            throws ComponentException;

    /**
     * To update the row
     * 
     * @param id
     *            - caching key
     * @param T
     *            - object in request
     * @param query
     *            - query to be fired against DB
     * @param cacheDuration
     *            - The amount of time the object to be stored in cache
     * @param params
     *            - column values to be inserted
     * @throws ComponentException
     */
    public <T> void update(Serializable id, Class<T> T, String query,
            CacheDuration cacheDuration, Object... params)
            throws ComponentException;

    /**
     * To hard or soft delete the row, depends on the query
     * 
     * @param id
     *            - caching key
     * @param T
     *            - object in request
     * @param query
     *            - query to be fired against DB
     * @throws ComponentException
     */
    public <T> void delete(Serializable id, Class<T> T, String query)
            throws ComponentException;
    /**
     * To update the row
     * 
     * @param id
     *            - caching key
     * @param T
     *            - object in request
     * @param query
     *            - query to be fired against DB
     * @param cacheDuration
     *            - The amount of time the object to be stored in cache
     * @param params
     *            - column values to be inserted
     * @throws ComponentException
     */
    public <T> void updateWithLock(Serializable id, Class<T> T, String query,
            CacheDuration cacheDuration, Object... params) throws ComponentException, SQLException;
}
