package com.dvd.rental.capstone.external;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

/**
 * Transfer object representing Borrow
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowTo {
    /**
     * Title of the title
     */
    private String dvdTitle;
    /**
     * Due date of the DVD
     */
    private DateTime dueDate;
    /**
     * this will be only used to show for output
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
}
