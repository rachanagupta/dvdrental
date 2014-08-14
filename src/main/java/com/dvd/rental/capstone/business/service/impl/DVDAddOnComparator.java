
package com.dvd.rental.capstone.business.service.impl;

import java.util.Comparator;

import com.dvd.rental.capstone.business.entity.DVD;

/**
 * DVDAddOnComparator 
 * 
 * Will sort the dvds by the date they were added 
 * @author rachana
 * @since  Aug 10, 2014
 * 
 */
public class DVDAddOnComparator implements Comparator<DVD>{

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(DVD o1, DVD o2) {
        if(o1 == null) {
            return 1;
        }
        if(o2 == null) {
            return -1;
        }
        return o1.getAddedOn().compareTo(o2.getAddedOn());
    }

}
