package com.dvd.rental.capstone.business.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dvd.rental.capstone.business.entity.DVD;
import com.dvd.rental.capstone.business.entity.Status;

/**
 * To convert the result set in to object.
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
public class DVDResultSetExtractor implements ResultSetExtractor<DVD> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
     * .ResultSet)
     */
    @Override
    public DVD extractData(ResultSet rs) throws SQLException,
            DataAccessException {
        //We can make the move the hard coded column names to the Constants
        return new DVD().setId(UUID.fromString(rs.getString("id")))
                .setPrice(rs.getDouble("price"))
                .setDescripton(rs.getString("description"))
                .setAddedOn(new DateTime(rs.getDate("added_on")))
                .setUserId(UUID.fromString(rs.getString("user_id")))
                .setStatus(Status.fromValue(rs.getInt("status")))
                .setMovieId(UUID.fromString(rs.getString("movie_id")))
                .setTitle(rs.getString("title"));
    }
}
