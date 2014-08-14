package com.dvd.rental.capstone.business.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dvd.rental.capstone.business.entity.Movie;

/**
 * To convert the result set in to object.
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
public class MovieRowMapper implements RowMapper<Movie> {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
     * int)
     */
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        MovieResultSetExtractor extractor = new MovieResultSetExtractor();
        return extractor.extractData(rs);
    }

}
