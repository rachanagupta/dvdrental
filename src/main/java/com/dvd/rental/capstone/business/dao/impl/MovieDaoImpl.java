package com.dvd.rental.capstone.business.dao.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dvd.rental.capstone.business.dao.Constants;
import com.dvd.rental.capstone.business.dao.MovieDao;
import com.dvd.rental.capstone.business.dao.common.AbstractDao;
import com.dvd.rental.capstone.business.dao.common.CacheDuration;
import com.dvd.rental.capstone.business.dao.mapper.MovieRowMapper;
import com.dvd.rental.capstone.business.entity.Movie;
import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * MovieDaoImpl - For handling DB operations on Movie Entity
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
@Component
public class MovieDaoImpl extends AbstractDao implements MovieDao {

    /*
     * (non-Javadoc)
     * @see com.dvd.rental.capstone.business.dao.MovieDao#findById(java.util.UUID)
     */
    public Movie findById(UUID id) throws ComponentException {
        String sql = "SELECT " + Constants.Movie.FIELDS + " FROM "
                + Constants.Movie.TABLE + " WHERE id = ? ";
        return findById(id, Movie.class, sql, new MovieRowMapper(), CacheDuration.Short, new Object[] { id.toString() });
    }
    /*
     * (non-Javadoc)
     * @see com.dvd.rental.capstone.business.dao.MovieDao#insert(com.dvd.rental.capstone.business.entity.Movie)
     */
    public void insert(Movie movie) throws ComponentException {
        assertThat(movie).isNotNull();
        String sql = "INSERT INTO " + Constants.Movie.TABLE + " ( "
                + Constants.Movie.FIELDS + " ) VALUES (?, ?, ?, ?, ?) ";
        create(movie.getId(), Movie.class, sql, CacheDuration.Short, new Object[] {
            UUID.randomUUID().toString(), movie.getName(),
            movie.getGenre(), movie.getStudio(), movie.getYear() });
    }

}
