package org.smart4j.chapter2.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by lenovo on 2016-12-23.
 */
public final class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?,?> map){
        return MapUtils.isEmpty(map);
    }
    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }

}