package com.dvd.rental.capstone.business.service.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dvd.rental.capstone.business.entity.Borrow;
import com.dvd.rental.capstone.business.entity.DVD;
import com.dvd.rental.capstone.business.entity.Status;
import com.dvd.rental.capstone.business.exception.ComponentException;
import com.dvd.rental.capstone.business.service.DVDManager;

/**
 * @author rachana
 * @since Aug 10, 2014
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ManagerIntegrationTest {
    /**
     * Test data
     */
    UUID testId = UUID.fromString("25359b8c-36ec-405d-9cef-eff7bff22587");
    UUID testUserId = UUID.fromString("b96441f1-1ce3-47f4-a93c-73568e02e98a");

    /**
     * instance of {@link DVDManager} 
     */
    @Autowired
    DVDManager manager;

    /**
     * To test search
     * 
     * @throws ComponentException
     */
    @Test
    public void search() throws ComponentException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("price", new Double(9.99));
        map.put("description", null);
        List<DVD> dvds = manager.search(map);
        assertThat(dvds).isNotEmpty();
        for (DVD dvd : dvds) {
            assertThat(dvd.getId()).isNotNull();
            assertThat(dvd.getPrice()).isEqualTo(new Double(9.99));
        }
    }

    /**
     * Negative test case to check proper exception is thrown for invalid input
     * 
     * @throws ComponentException
     */
    @Test(expected = ComponentException.class)
    public void search_negative() throws ComponentException {
        manager.search(null);
    }

    /**
     * Test findById method of manager
     * 
     * @throws ComponentException
     */
    @Test
    public void findById() throws ComponentException {
        DVD dvd = manager.findDVDById(testId);
        assertThat(dvd).isNotNull();
        assertThat(dvd.getId()).isNotNull().isEqualTo(testId);
    }

    /**
     * Negative test case to check proper exception is thrown for invalid input
     * 
     * @throws ComponentException
     */
    @Test(expected = ComponentException.class)
    public void findById_negative() throws ComponentException {
        manager.findDVDById(null);
    }

    /**
     * To test share method
     * 
     * @throws ComponentException
     */
    @Test
    public void share() throws ComponentException {
        manager.share(Arrays.asList(testId), testUserId);
        DVD dvd = manager.findDVDById(testId);
        assertThat(dvd).isNotNull();
        assertThat(dvd.getId()).isNotNull().isEqualTo(testId);
        assertThat(dvd.getStatus()).isNotNull().isEqualTo(Status.AVAILABLE);
    }

    /**
     * To test share method
     * 
     * @throws ComponentException
     */
    @Test(expected = ComponentException.class)
    public void share_negative() throws ComponentException {
        manager.share(null, testUserId);
    }

    /**
     * To test borrow method
     * 
     * @throws ComponentException
     */
    @Test
    public void borrow() throws ComponentException {
        Borrow borrow = new Borrow();
        borrow.setDvdTitle("StarWars");
        borrow.setUserId(testUserId);
        manager.borrow(borrow);
        DVD dvd = manager.findDVDById(testId);

        assertThat(dvd).isNotNull();
        assertThat(dvd.getId()).isNotNull().isEqualTo(testId);
        assertThat(dvd.getStatus()).isNotNull().isEqualTo(Status.TAKEN);
    }

    /**
     * To test share method
     * 
     * @throws ComponentException
     */
    @Test(expected = ComponentException.class)
    public void borrow_negative() throws ComponentException {
        manager.borrow(null);
    }
}
