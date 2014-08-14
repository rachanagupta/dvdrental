package com.dvd.rental.capstone.business.entity;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

/**
 * Movie - contains the information about the movie
 * 
 * @author rachana
 * @since Aug 8, 2014
 * 
 */
public class Movie implements Serializable {
    /**
     * serial id
     */
    private static final long serialVersionUID = 7342807314327178879L;
    /**
     * Unique identifier of the movie
     */
    private UUID id;
    /**
     * Name of the movie
     */
    private String name;
    /**
     * Genre of the movie
     */
    private String genre;
    /**
     * Studio who produced the movie
     */
    private String studio;
    /**
     * {@link DateTime} when movie released
     */
    private DateTime releaseOn;
    /**
     * Year when released
     */
    private int year;

    /**
     * String representation of the movie content
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public Movie setId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public Movie setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre
     *            the genre to set
     */
    public Movie setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    /**
     * @return the studio
     */
    public String getStudio() {
        return studio;
    }

    /**
     * @param studio
     *            the studio to set
     */
    public Movie setStudio(String studio) {
        this.studio = studio;
        return this;
    }

    /**
     * @return the releaseOn
     */
    public DateTime getReleaseOn() {
        return releaseOn;
    }

    /**
     * @param releaseOn
     *            the releaseOn to set
     */
    public Movie setReleaseOn(DateTime releaseOn) {
        this.releaseOn = releaseOn;
        return this;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year
     *            the year to set
     */
    public Movie setYear(int year) {
        this.year = year;
        return this;
    }
}
