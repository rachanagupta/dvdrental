package com.dvd.rental.capstone.business.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dvd.rental.capstone.business.service.impl.EmailManager;

/**
 * To test the send method of email 
 * 
 * TODO : Add the negative test cases 
 * 
 * @author rachana
 * @since  Aug 10, 2014
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EmailManagerIntegrationTest {
    /**
     * {@link EmailManager} 
     */
    @Autowired
    EmailManager emailManager;
    /**
     * Test case to test send method of {@link EmailManager}
     */
    @Test
    public void send() {
        emailManager.send("temp@gmail.com", "test subject", "test body");
    }
}
