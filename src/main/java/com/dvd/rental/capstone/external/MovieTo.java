package com.dvd.rental.capstone.external;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

/**
 * @author rachana
 * @since Aug 8, 2014
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MovieTo {
    private UUID id;
    private String name;
    private String genre;
    private String studio;
    private DateTime releaseOn; 
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
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }
    /**
     * @param genre the genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    /**
     * @return the studio
     */
    public String getStudio() {
        return studio;
    }
    /**
     * @param studio the studio to set
     */
    public void setStudio(String studio) {
        this.studio = studio;
    }
    /**
     * @return the releaseOn
     */
    public DateTime getReleaseOn() {
        return releaseOn;
    }
    /**
     * @param releaseOn the releaseOn to set
     */
    public void setReleaseOn(DateTime releaseOn) {
        this.releaseOn = releaseOn;
    }
    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }
    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }
}
