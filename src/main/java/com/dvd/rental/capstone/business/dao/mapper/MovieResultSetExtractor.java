package com.dvd.rental.capstone.business.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dvd.rental.capstone.business.entity.Movie;

/**
 * To convert the result set in to object.
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
public class MovieResultSetExtractor implements ResultSetExtractor<Movie> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
     * .ResultSet)
     */
    @Override
    public Movie extractData(ResultSet rs) throws SQLException,
            DataAccessException {
        return new Movie().setId(UUID.fromString(rs.getString("id")))
                .setGenre(rs.getString("genre")).setName(rs.getString("name"))
                .setStudio(rs.getString("studio")).setYear(rs.getInt("year"));
    }

}
