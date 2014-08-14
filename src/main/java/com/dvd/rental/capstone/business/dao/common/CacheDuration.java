package com.dvd.rental.capstone.business.dao.common;

/**
 * Constants for defining the cache timeout
 * 
 * @author rachana
 * @since Aug, 12 2014
 */
public enum CacheDuration {
    /**
     * Do not cache
     */
    DoNotCache(0),
    /**
     * One minute
     */
    VeryShort(60),
    /**
     * Five minutes
     */
    Short(60 * 5),
    /**
     * One hour
     */
    Moderate(3600),
    /**
     * Twelve hours
     */
    Long(3600 * 12),
    /**
     * One day
     */
    VeryLong(3600 * 24),
    /**
     * A long time
     */
    Forever(Integer.MAX_VALUE); // ~64 years, just FYI

    private final int seconds;

    private CacheDuration(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
}
