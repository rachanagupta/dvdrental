package com.dvd.rental.capstone.business.dao;

import java.util.UUID;

import com.dvd.rental.capstone.business.entity.Movie;
import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * @author rachana
 * @since  Aug 10, 2014
 * 
 */
public interface MovieDao {
    /**
     * To find the movie by id
     * @param id {@link UUID}
     * @return {@link Movie}
     */
    public Movie findById(UUID id) throws ComponentException;
    /**
     * To insert the movie
     * @param movie
     */
    public void insert(Movie movie) throws ComponentException;
}
