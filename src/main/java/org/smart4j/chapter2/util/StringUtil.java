package org.smart4j.chapter2.util;

//import org.apache.*;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by lenovo on 2016-12-23.
 */
public final class StringUtil {
    public static boolean isNotEmpty(String strValue) {
        return !isEmpty(strValue);
    }

    private static boolean isEmpty(String strValue) {
        if (strValue!=null){
            strValue=strValue.trim();
        }
        return StringUtils.isEmpty(strValue);
    }
}
