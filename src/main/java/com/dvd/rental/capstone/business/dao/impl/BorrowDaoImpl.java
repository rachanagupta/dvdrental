package com.dvd.rental.capstone.business.dao.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import org.springframework.stereotype.Component;

import com.dvd.rental.capstone.business.dao.BorrowDao;
import com.dvd.rental.capstone.business.dao.Constants;
import com.dvd.rental.capstone.business.dao.common.AbstractDao;
import com.dvd.rental.capstone.business.dao.common.CacheDuration;
import com.dvd.rental.capstone.business.entity.Borrow;
import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * This class will take care assigning the DVD as borrowed
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
@Component
public class BorrowDaoImpl extends AbstractDao implements BorrowDao {
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dvd.rental.capstone.business.dao.BorrowDao#insert(com.dvd.rental.
     * capstone.business.entity.Borrow)
     */
    public void insert(Borrow borrow) throws ComponentException {
        assertThat(borrow).isNotNull();
        String sql = "INSERT INTO " + Constants.Borrow.TABLE
                + "(`dvd_id`, `user_id`, `due_date`)  VALUES (?, ?, ?) ";
        create(borrow.getDvdId(), Borrow.class, sql, CacheDuration.Short, new Object[] {
            borrow.getDvdId().toString(), borrow.getUserId().toString(),
            borrow.getDueDate() });
        
    }
}
