package com.dvd.rental.capstone.business.entity;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

/**
 * Assumption : Each DVD only contains 1 movie.
 * 
 * @author rachana
 * @since  Aug, 8 2014
 */
public class DVD implements Serializable {
    /**
     * serial id
     */
    private static final long serialVersionUID = -8383652601171801363L;
    /**
     * Unique identifier of the DVD
     */
    private UUID id;
    /**
     * Unique identifier of the movie 
     */
    private UUID movieId;
    /**
     * Price at which it should be rented
     */
    private Double price;
    /**
     * Unique identifier of the user who wants to lend the DVD
     */
    private UUID userId;
    /**
     * Title of the DVD
     */
    private String title;
    /**
     * Description of the DVD
     */
    private String descripton;
    /**
     * The date on which it is been added
     */
    private DateTime addedOn;
    /**
     *{@link Status} current status of the DVD
     */
    private Status status; 
    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public DVD setId(UUID id) {
        this.id = id;
        return this;
    }
    /**
     * @return the movieId
     */
    public UUID getMovieId() {
        return movieId;
    }
    /**
     * @param movieId the movieId to set
     */
    public DVD setMovieId(UUID movieId) {
        this.movieId = movieId;
        return this;
    }
    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public DVD setPrice(Double price) {
        this.price = price;
        return this;
    }
    /**
     * @return the userId
     */
    public UUID getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public DVD setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }
    /**
     * @return the descripton
     */
    public String getDescripton() {
        return descripton;
    }
    /**
     * @param descripton the descripton to set
     */
    public DVD setDescripton(String descripton) {
        this.descripton = descripton;
        return this;
    }
    /**
     * @return the addedOn
     */
    public DateTime getAddedOn() {
        return addedOn;
    }
    /**
     * @param addedOn the addedOn to set
     */
    public DVD setAddedOn(DateTime addedOn) {
        this.addedOn = addedOn;
        return this;
    }
    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public DVD setStatus(Status status) {
        this.status = status;
        return this;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public DVD setTitle(String title) {
        this.title = title;
        return this;
    }
    /**
     * String representation of the borrow content
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
   
    /**
     * HashCode
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(31, 11).append(id).append(movieId)
                .append(price).append(userId).append(title).append(addedOn).append(status).hashCode();
    }

    /**
     * Equals method
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DVD dvd = (DVD) obj;
        return new EqualsBuilder().append(id, dvd.id)
                .append(movieId, dvd.movieId).append(userId, dvd.userId)
                .append(price, dvd.price)
                .append(title, dvd.title)
                .append(addedOn, dvd.addedOn)
                .append(status, dvd.status)
                .isEquals();
    }
}
