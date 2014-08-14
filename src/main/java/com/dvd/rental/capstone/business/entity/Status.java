package com.dvd.rental.capstone.business.entity;

/**
 * Defines the status of the DVD
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
public enum Status {
    TAKEN(0), //DVD is been rented
    AVAILABLE(1), //DVD is available to rent
    FRIED(2); // DVD is fried up so cannot be rented again

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Status fromValue(int value) {
        switch (value) {
        case 0:
            return TAKEN;
        case 1:
            return AVAILABLE;
        case 2:
            return FRIED;
        default:
            return TAKEN;
        }
    }
}
