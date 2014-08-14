package com.dvd.rental.capstone.business.dao;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.dvd.rental.capstone.business.entity.DVD;
import com.dvd.rental.capstone.business.entity.Status;
import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * @author rachana
 * @since Aug 10, 2014
 * 
 */
public interface DVDDao {
    /**
     * To find the DVD by Id
     * 
     * @param id
     *            {@link UUID}
     * @return {@link DVD}
     */
    public DVD findById(UUID id) throws ComponentException;

    /**
     * To insert the DVD information into table
     * 
     * @param dvd
     *            {@link DVD}
     */
    public void insert(DVD dvd) throws ComponentException;

    /**
     * To update the status of DVD to either Fired, Available, Taken Note : I am
     * using isolation level TRANSACTION_SERIALIZABLE to avoid concurrent thread
     * updating the same row at the same time
     * 
     * @param id
     *            {@link UUID}
     * @param status
     *            {@link Status}
     */
    public void updateStatus(UUID id, Status status) throws ComponentException;

    /**
     * To search the DVD Assumption : Text search is equals not likes
     * 
     * @param whereMapper
     *            {@link Map}
     * @return {@link List}
     */
    public List<DVD> search(Map<String, Object> whereMapper)
            throws ComponentException;

    /**
     * Find the available dvds by Title, same method can be overloaded to find
     * the available DVDs by other attributes Note : not adding ordering to it
     * as it can slow down the query
     * 
     * @param title
     * @return
     */
    public List<DVD> findAvailableDVDs(String title) throws ComponentException;
}
