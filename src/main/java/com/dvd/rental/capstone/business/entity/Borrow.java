package com.dvd.rental.capstone.business.entity;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

/**
 * DO classes to interact between api and database
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
public class Borrow implements Serializable {
    /**
     * serial id
     */
    private static final long serialVersionUID = 4562942697895157133L;
    /**
     * Title of the dvd 
     */
    private String dvdTitle;
    /**
     * Unique identifier of the dvd
     */
    private UUID dvdId;
    /**
     * User who is borrowing te dvd 
     */
    private UUID userId;
    /**
     * When the DVD is due
     */
    private DateTime dueDate;
    /**
     * When the DVD was borrowed
     */
    private DateTime borrowedOn;

    /**
     * @return the dvdTitle
     */
    public String getDvdTitle() {
        return dvdTitle;
    }

    /**
     * @param dvdTitle
     *            the dvdTitle to set
     */
    public void setDvdTitle(String dvdTitle) {
        this.dvdTitle = dvdTitle;
    }

    /**
     * @return the userId
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * @return the dueDate
     */
    public DateTime getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate
     *            the dueDate to set
     */
    public void setDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the borrowedOn
     */
    public DateTime getBorrowedOn() {
        return borrowedOn;
    }

    /**
     * @param borrowedOn
     *            the borrowedOn to set
     */
    public void setBorrowedOn(DateTime borrowedOn) {
        this.borrowedOn = borrowedOn;
    }

    /**
     * @return the dvdId
     */
    public UUID getDvdId() {
        return dvdId;
    }

    /**
     * @param dvdId
     *            the dvdId to set
     */
    public void setDvdId(UUID dvdId) {
        this.dvdId = dvdId;
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
        return new HashCodeBuilder(31, 11).append(dvdTitle).append(dvdId)
                .append(userId).append(dueDate).append(borrowedOn).hashCode();
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
        Borrow borrow = (Borrow) obj;
        return new EqualsBuilder().append(dvdTitle, borrow.dvdTitle)
                .append(dvdId, borrow.dvdId).append(userId, borrow.userId)
                .append(dueDate, borrow.dueDate)
                .append(borrowedOn, borrow.borrowedOn).isEquals();
    }

}
