package com.dvd.rental.capstone.business.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.dvd.rental.capstone.business.entity.Borrow;
import com.dvd.rental.capstone.business.entity.DVD;
import com.dvd.rental.capstone.business.entity.Movie;
import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * @author rachana
 * @since Aug 10, 2014
 * 
 */
public interface DVDManager {
    /**
     * Find DVD by Id
     * 
     * @param id
     *            {@link UUID}
     * @return {@link DVD}
     * @throws ComponentException
     */
    public DVD findDVDById(UUID id) throws ComponentException;

    /**
     * Find the Movie by Id
     * 
     * @param id
     *            {@link UUID}
     * @return {@link Movie}
     * @throws ComponentException
     */
    public Movie findMovieById(UUID id) throws ComponentException;

    /**
     * To share the DVDs
     * 
     * @param dvdIds
     *            {@link List}
     * @param userId
     *            {@link UUID}
     * @throws ComponentException
     */
    public void share(List<UUID> dvdIds, UUID userId) throws ComponentException;

    /**
     * To borrow the DVDs Assumption : You can borrow only 1 dvd at a time
     * 
     * @param borrow
     * @throws ComponentException
     */
    public void borrow(Borrow borrow) throws ComponentException;

    /**
     * To search the DVDs
     * 
     * @param criteria
     *            {@link Map} will be used to create the whereClause
     * @return {@link List}
     * @throws ComponentException
     */
    public List<DVD> search(Map<String, Object> criteria)
            throws ComponentException;
}
