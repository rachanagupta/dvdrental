package com.dvd.rental.capstone.business.dao;

import com.google.common.collect.ImmutableMap;

/**
 * @author rachana
 * @since Aug 10, 2014
 * 
 */
public abstract class Constants {
    public static final String DOT = ".";

    public static class DVD_MOVIE_MAPPING {
        public static final String TABLE = "dvd_movie_mapping";
    }

    public static class DVD {
        public static final String TABLE = "dvd";
        public static final String FIELDS = "id, price, user_id, status, title, description, added_on, movie_id";
        public static final String ALIAS = "d";
        public static final ImmutableMap<String, String> ALIASED_MAPPING = new ImmutableMap.Builder<String, String>()
                .put("id", ALIAS + DOT + "id")
                .put("price", ALIAS + DOT + "price")
                .put("userId", ALIAS + DOT + "user_id")
                .put("title", ALIAS + DOT + "title")
                .put("status", ALIAS + DOT + "status")
                .put("description", ALIAS + DOT + "description")
                .put("addedOn", ALIAS + DOT + "added_on")
                .put("movieId", ALIAS + DOT + "movie_id").build();
    }

    public static class Movie {
        public static final String TABLE = "movie";
        public static final String FIELDS = "`id`, `name`, `genre`, `studio`, `year`";
        public static final String ALIAS = "m";
        public static final ImmutableMap<String, String> ALIASED_MAPPING = new ImmutableMap.Builder<String, String>()
                .put("id", ALIAS + DOT + "id")
                .put("name", ALIAS + DOT + "name")
                .put("genre", ALIAS + DOT + "genre")
                .put("studio", ALIAS + DOT + "studio")
                .put("year", ALIAS + DOT + "year").build();
    }

    public static class Borrow {
        public static final String FIELDS = "`dvd_id`, `user_id`, `due_date`, `borrowed_on`";
        public static final String TABLE = "borrowed";
    }
}
