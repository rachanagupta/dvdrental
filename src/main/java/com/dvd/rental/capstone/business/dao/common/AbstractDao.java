package com.dvd.rental.capstone.business.dao.common;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dvd.rental.capstone.business.dao.mapper.DVDRowMapper;
import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * This class will abstract out the common code of the dao with Caching
 * 
 * TODO : To add caching
 * 
 * @author rachana
 * @since Aug, 10 2014
 * 
 */
public class AbstractDao implements Dao {
    /**
     * {@link DataSource}
     */
    @Autowired
    private DataSource dataSource;

    /**
     * @param dataSource
     *            the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        assertThat(this.dataSource).isNotNull();
    }
    /*
     * (non-Javadoc)
     * @see com.dvd.rental.capstone.business.dao.common.Dao#create(java.io.Serializable, java.lang.Class, java.lang.String, com.dvd.rental.capstone.business.dao.common.CacheDuration, java.lang.Object[])
     */
    @Override
    public <T> void create(Serializable id, Class<T> T, String query,
            CacheDuration cacheDuration, Object... params)
            throws ComponentException {
        // add caching logic around it
        new JdbcTemplate(dataSource).update(query, params);
    }

    /*
     * (non-Javadoc)
     * @see com.dvd.rental.capstone.business.dao.common.Dao#update(java.io.Serializable, java.lang.Class, java.lang.String, com.dvd.rental.capstone.business.dao.common.CacheDuration, java.lang.Object[])
     */
    @Override
    public <T> void update(Serializable id, Class<T> T, String query,
            CacheDuration cacheDuration, Object... params)
            throws ComponentException {
        new JdbcTemplate(dataSource).update(query, params);
    }
    
    /*
     * (non-Javadoc)
     * @see com.dvd.rental.capstone.business.dao.common.Dao#updateWithLock(java.io.Serializable, java.lang.Class, java.lang.String, com.dvd.rental.capstone.business.dao.common.CacheDuration, java.lang.Object[])
     */
    @Override
    public <T> void updateWithLock(Serializable id, Class<T> T, String query,
            CacheDuration cacheDuration, Object... params)
            throws ComponentException, SQLException {
        JdbcTemplate update = new JdbcTemplate(dataSource);
        update.getDataSource()
        .getConnection()
        .setTransactionIsolation(
                Connection.TRANSACTION_SERIALIZABLE);
        update.update(query, params);
    }

    /*
     * (non-Javadoc)
     * @see com.dvd.rental.capstone.business.dao.common.Dao#findById(java.io.Serializable, java.lang.Class, java.lang.String, org.springframework.jdbc.core.RowMapper, com.dvd.rental.capstone.business.dao.common.CacheDuration, java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T findById(Serializable id, Class<T> T, String query,
            RowMapper<T> rowMapper, CacheDuration cacheDuration,
            Object... params) throws ComponentException {
        // add caching logic around it
        return (T) new JdbcTemplate(dataSource).queryForObject(query, params,
                new DVDRowMapper());
    }

    /*
     * (non-Javadoc)
     * @see com.dvd.rental.capstone.business.dao.common.Dao#find(java.io.Serializable, java.lang.Class, java.lang.String, org.springframework.jdbc.core.RowMapper, com.dvd.rental.capstone.business.dao.common.CacheDuration, java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> find(Serializable id, Class<T> T, String query,
            RowMapper<T> rowMapper, CacheDuration cacheDuration,
            Object... params) throws ComponentException {
        return (List<T>) new JdbcTemplate(dataSource).queryForList(query,
                rowMapper);
    }

    /*
     * (non-Javadoc)
     * @see com.dvd.rental.capstone.business.dao.common.Dao#delete(java.io.Serializable, java.lang.Class, java.lang.String)
     */
    @Override
    public <T> void delete(Serializable id, Class<T> T, String query)
            throws ComponentException {
        throw new ComponentException.UnsupportedOperation();
    }

}
