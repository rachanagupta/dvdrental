package com.dvd.rental.capstone.business.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.dvd.rental.capstone.business.dao.BorrowDao;
import com.dvd.rental.capstone.business.dao.DVDDao;
import com.dvd.rental.capstone.business.dao.MovieDao;
import com.dvd.rental.capstone.business.dao.impl.DVDDaoImpl;
import com.dvd.rental.capstone.business.dao.impl.MovieDaoImpl;
import com.dvd.rental.capstone.business.entity.Borrow;
import com.dvd.rental.capstone.business.entity.DVD;
import com.dvd.rental.capstone.business.entity.Movie;
import com.dvd.rental.capstone.business.entity.Status;
import com.dvd.rental.capstone.business.exception.ComponentException;
import com.dvd.rental.capstone.business.exception.ComponentException.InvalidRequest;
import com.dvd.rental.capstone.business.service.DVDManager;

/**
 * DVDManagerImpl - Manager class to apply the logic
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
@Service
public class DVDManagerImpl implements DVDManager {
    /**
     * {@link DVDDaoImpl}
     */
    @Autowired
    private DVDDao dvdDao;
    /**
     * {@link MovieDaoImpl}
     */
    @Autowired
    private MovieDao movieDao;
    /**
     * {@link BorrowDao}
     */
    @Autowired
    private BorrowDao borrowDao;
    /**
     * {@link EmailManager}
     */
    @Autowired
    EmailManager emailManager;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dvd.rental.capstone.business.service.DVDManager#findDVDById(java.
     * util.UUID)
     */
    public DVD findDVDById(UUID id) throws ComponentException {
        if (id == null) {
            throw new InvalidRequest(" dvd id cannot be null");
        }
        return dvdDao.findById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dvd.rental.capstone.business.service.DVDManager#findMovieById(java
     * .util.UUID)
     */
    public Movie findMovieById(UUID id) throws ComponentException {
        if (id == null) {
            throw new InvalidRequest(" dvd id cannot be null");
        }
        return movieDao.findById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dvd.rental.capstone.business.service.DVDManager#share(java.util.List,
     * java.util.UUID)
     */
    public void share(List<UUID> dvdIds, UUID userId) throws ComponentException {
        if (CollectionUtils.isEmpty(dvdIds)) {
            throw new InvalidRequest("Empty DVDs to share");
        }
        if (CollectionUtils.isNotEmpty(dvdIds)) {
            for (UUID id : dvdIds) {
                DVD dvd = dvdDao.findById(id);
                // check if the user who is intending to share is the holder of
                // of the dvd
                if (!dvd.getUserId().equals(userId)) {
                    throw new InvalidRequest(
                            " Only owner of the DVD can share it");
                }
                if (dvd.getStatus().equals(Status.FRIED)) {
                    throw new InvalidRequest(" Cannot share fried DVD");
                }
                dvdDao.updateStatus(id, Status.AVAILABLE);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dvd.rental.capstone.business.service.DVDManager#borrow(com.dvd.rental
     * .capstone.business.entity.Borrow)
     */
    @Transactional(
            rollbackFor = Exception.class,
            isolation = Isolation.SERIALIZABLE)
    public void borrow(Borrow borrow) throws ComponentException {
        if (borrow == null) {
            throw new InvalidRequest(" borrow  cannot be null");
        }
        if (StringUtils.isBlank(borrow.getDvdTitle())) {
            throw new InvalidRequest(" dvd title  cannot be null");
        }
        if (borrow.getUserId() == null) {
            throw new InvalidRequest(" borrower id cannot be null");
        }
        List<DVD> availableDVDs = dvdDao
                .findAvailableDVDs(borrow.getDvdTitle());
        if (CollectionUtils.isNotEmpty(availableDVDs)) {
            // sort by dvds add to the system to give the first lender chance to
            // rent his title first
            Collections.sort(availableDVDs, new DVDAddOnComparator());
            // remove the first DVD
            DVD dvd = availableDVDs.get(0);
            // check again that DVD is not null and status to make sure threads
            // have not overwritten the status
            if (dvd != null
                    && dvd.getStatus().equals(Status.AVAILABLE.getValue())) {
                // mark the dvd as taken
                dvdDao.updateStatus(dvd.getId(), Status.TAKEN);
                borrow.setDvdId(dvd.getId());
                // add them to borrowed table
                borrowDao.insert(borrow);
                // when all the updates to the table are successful send email
                // to the lender
                // Note : Hard coding the email as we dont have user table - out
                // of scope of this project
                emailManager.send("temp@gmail.com", "You DVD is been rented ",
                        " You DVD " + dvd.getTitle() + " is been rented");
            }
        } else {
            throw new ComponentException.NotFound("Not DVD to lend found");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dvd.rental.capstone.business.service.DVDManager#search(java.util.Map)
     */
    public List<DVD> search(Map<String, Object> criteria)
            throws ComponentException {
        if (criteria == null) {
            throw new InvalidRequest("Empty criteria");
        }
        return dvdDao.search(criteria);
    }
}
