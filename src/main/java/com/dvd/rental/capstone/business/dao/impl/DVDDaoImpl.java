package com.dvd.rental.capstone.business.dao.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.dvd.rental.capstone.business.dao.Constants;
import com.dvd.rental.capstone.business.dao.DVDDao;
import com.dvd.rental.capstone.business.dao.common.AbstractDao;
import com.dvd.rental.capstone.business.dao.common.CacheDuration;
import com.dvd.rental.capstone.business.dao.mapper.DVDRowMapper;
import com.dvd.rental.capstone.business.entity.DVD;
import com.dvd.rental.capstone.business.entity.Status;
import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * DVDDaoImpl - For handling the operation over the DVD
 * 
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
@Component
public class DVDDaoImpl extends AbstractDao implements DVDDao {

    /*
     * (non-Javadoc)
     * 
     * @see com.dvd.rental.capstone.business.dao.DVDDao#findById(java.util.UUID)
     */
    public DVD findById(UUID id) throws ComponentException {
        String sql = "SELECT " + Constants.DVD.FIELDS + " FROM "
                + Constants.DVD.TABLE + " d join "
                + Constants.DVD_MOVIE_MAPPING.TABLE
                + " m on d.id = m.dvd_id WHERE id = ?";
        return (DVD) findById(id, DVD.class, sql, new DVDRowMapper(), CacheDuration.Short, new Object[] { id.toString() });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dvd.rental.capstone.business.dao.DVDDao#insert(com.dvd.rental.capstone
     * .business.entity.DVD)
     */
    public void insert(DVD dvd) throws ComponentException {
        // validate the attributes
        assertThat(dvd).isNotNull();
        assertThat(dvd.getMovieId()).isNotNull();
        String sql = "INSERT INTO "
                + Constants.DVD.TABLE
                + " (`id`, `price`, `user_id`, `status`, `description`) VALUES (?, ?, ?, ?, ?) ";
        UUID dvdId = UUID.randomUUID();
        //create
        create(dvd.getId(), DVD.class, sql, CacheDuration.Short, new Object[] { dvdId.toString(), dvd.getPrice(),
            dvd.getUserId(), dvd.getStatus().getValue(),
            dvd.getDescripton() });
        
        //update
        sql = "INSERT INTO " + Constants.DVD_MOVIE_MAPPING.TABLE
                + " (`dvd_id`, `movie_id`) VALUES (? , ?)";
        update(dvd.getId(), DVD.class, sql, CacheDuration.Short, new Object[] { dvdId.toString(), dvd.getMovieId().toString() });

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dvd.rental.capstone.business.dao.DVDDao#updateStatus(java.util.UUID,
     * com.dvd.rental.capstone.business.entity.Status)
     */
    public void updateStatus(UUID id, Status status) throws ComponentException {
        assertThat(id).isNotNull();
        String sql = "UPDATE " + Constants.DVD.TABLE
                + " SET `status` = ? where id = ? ";
        
        try {
            updateWithLock(id, DVD.class, sql, CacheDuration.Short, new Object[] { status.getValue(), id.toString() });
        } catch (SQLException e) {
            throw new ComponentException.ConfigurationException("Sql Exception");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dvd.rental.capstone.business.dao.DVDDao#search(java.util.Map)
     */
    public List<DVD> search(Map<String, Object> whereMapper) throws ComponentException {
        // Note : below logic can be taken to the common utils
        String whereClause = null;
        if (!MapUtils.isEmpty(whereMapper)) {
            whereClause = " WHERE ";
            StringBuilder w = new StringBuilder();
            Iterator<Entry<String, Object>> entries = whereMapper.entrySet()
                    .iterator();
            while (entries.hasNext()) {
                Entry<String, Object> thisEntry = (Entry<String, Object>) entries
                        .next();
                Object key = thisEntry.getKey();
                Object value = thisEntry.getValue();
                if (value != null) {
                    if (Constants.DVD.ALIASED_MAPPING.containsKey(key)) {
                        w.append(Constants.DVD.ALIASED_MAPPING.get(key));
                    }
                    if (Constants.Movie.ALIASED_MAPPING.containsKey(key)) {
                        w.append(Constants.Movie.ALIASED_MAPPING.get(key));
                    }
                    if (value.getClass().isAssignableFrom(String.class)
                            || value.getClass()
                                    .isAssignableFrom(DateTime.class)) {
                        w.append(" = '" + value + "'");
                    } else {
                        w.append(" = " + value);
                    }
                    if (entries.hasNext()) {
                        w.append(" AND ");
                    }
                }
            }
            whereClause += w.toString();
        }

        String sql = " SELECT d.id id, price, user_id, status, description, added_on, movie_id "
                + "  FROM dvd d join dvd_movie_mapping dm on d.id = dm.dvd_id join movie m on dm.movie_id=m.id "
                + whereClause;
        return find("find", DVD.class, sql, new DVDRowMapper(), CacheDuration.Short, new Object[]{});
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dvd.rental.capstone.business.dao.DVDDao#findAvailableDVDs(java.lang
     * .String)
     */
    @SuppressWarnings("rawtypes")
    public List<DVD> findAvailableDVDs(String title) throws ComponentException {
        String sql = "SELECT id from " + Constants.DVD.TABLE
                + " where title like ? AND status != ? AND status != ? ";
        
        @SuppressWarnings("unchecked")
        List<String> dvdIds = (List<String>)find("findAvailableDVDs", DVD.class, sql, new BeanPropertyRowMapper(
                        String.class), CacheDuration.Short,  new Object[] { title, Status.FRIED.getValue(),
                                Status.TAKEN.toString() });
        if (CollectionUtils.isNotEmpty(dvdIds)) {
            List<DVD> result = new ArrayList<DVD>();
            for (String id : dvdIds) {
                result.add(this.findById(UUID.fromString(id)));
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }
}
