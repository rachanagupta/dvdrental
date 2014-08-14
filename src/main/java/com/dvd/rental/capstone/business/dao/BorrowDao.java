package com.dvd.rental.capstone.business.dao;

import com.dvd.rental.capstone.business.entity.Borrow;
import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * @author rachana
 * @since Aug 10, 2014
 * 
 */
public interface BorrowDao {
    /**
     * To mark the DVD as borrow
     * 
     * @param borrow
     */
    public void insert(Borrow borrow) throws ComponentException;
}
