package com.dvd.rental.capstone.business.dao.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dvd.rental.capstone.business.dao.impl.DVDDaoImpl;
import com.dvd.rental.capstone.business.entity.DVD;
import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * To test all the methods of the dao class 
 * 
 * TODO : Add negative test cases
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DVDDaoImpIntegrationTest {
    /**
     * dao instance of {@link DVDDaoImpl}
     */
    @Autowired
    DVDDaoImpl dao;
    
    @Before 
    public void setup() {
        assertThat(dao).isNotNull();
    }
    /**
     * Test case for find by id 
     * @throws ComponentException
     */
    @Test
    public void findById() throws ComponentException {
        DVD dvd = dao.findById(UUID.fromString("3f533253-b5ac-48e5-b816-a19852bc9b07"));
        assertThat(dvd).isNotNull();
        assertThat(dvd.getId()).isEqualTo(UUID.fromString("3f533253-b5ac-48e5-b816-a19852bc9b07"));
    }
    /**
     * Test case for testing search
     * @throws ComponentException
     */
    @Test
    public void search() throws ComponentException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("price", new Double(9.99));
        List<DVD> dvds = dao.search(map);
        assertThat(dvds).isNotEmpty();
        for (DVD dvd : dvds) {
            assertThat(dvd.getId()).isNotNull();
            assertThat(dvd.getPrice()).isEqualTo(new Double(9.99));
        }
    }
    /**
     * Test case for finding available DVDs
     * @throws ComponentException
     */
    @Test
    public void findAvailable() throws ComponentException {
        List<DVD> dvds = dao.findAvailableDVDs("StarWars");
        assertThat(dvds).isNotEmpty();
        for (DVD dvd : dvds) {
            assertThat(dvd.getId()).isNotNull();
            assertThat(dvd.getTitle()).isEqualTo("StarWars");
        }
    }
    
}
