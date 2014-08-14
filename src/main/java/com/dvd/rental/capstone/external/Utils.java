package com.dvd.rental.capstone.external;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.dvd.rental.capstone.business.exception.ComponentException;
import com.dvd.rental.capstone.business.exception.ComponentException.InvalidRequest.IOException;

/**
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
public class Utils {
    public static <K, V> V transform(K source, V destination)
            throws ComponentException {
        try {
            BeanUtils.copyProperties(destination, source);
            return destination;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IOException("parsing exception");
        }
    }

    public static <K, V> List<V> transform(Collection<K> sources,
            Class<V> destinationClass) throws ComponentException {
        List<V> result = new ArrayList<V>();
        for (K source : sources) {
            try {
                result.add(transform(source, destinationClass.newInstance()));
            } catch (IOException | InstantiationException
                    | IllegalAccessException e) {
                throw new IOException("parsing exception");
            }
        }
        return result;
    }
}
