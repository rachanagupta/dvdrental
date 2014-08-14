package com.dvd.rental.capstone.external;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author rachana
 * @since  Aug 10, 2014
 * 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SearchTo {
    private Double price;
    private String name;
    private String description;
    private String genre;
    private Integer year;
    private String filmStudio;
    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return the year
     */
    public Integer getYear() {
        return year;
    }
    /**
     * @param year the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }
    /**
     * @return the filmStudio
     */
    public String getFilmStudio() {
        return filmStudio;
    }
    /**
     * @param filmStudio the filmStudio to set
     */
    public void setFilmStudio(String filmStudio) {
        this.filmStudio = filmStudio;
    }
}
