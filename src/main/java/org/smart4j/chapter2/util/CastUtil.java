package org.smart4j.chapter2.util;

/**
 * Created by lenovo on 2016-12-23.
 */
public final class CastUtil {
    public static String castString(Object object){
        return CastUtil.castString(object,"");
    }

    private static String castString(Object object, String defaultValue) {
        return object!=null ? String.valueOf(object):defaultValue;
    }

    public static double castDouble(Object object){
        return CastUtil.castDouble(object,0);
    }

    private static double castDouble(Object object, double defaultValue) {
        double doubleValue=defaultValue;
        if (object!=null){
            String strValue=castString(object);
            if (StringUtil.isNotEmpty(strValue)){
                doubleValue=Double.parseDouble(strValue);
            }
        }
        return doubleValue;
    }

    public static long castLong(Object object){
        return castLong(object,0);
    }

    private static long castLong(Object object, long defaultValue) {
        long longValue=defaultValue;
        if (object!=null){
            String strValue=castString(object);
            if (StringUtil.isNotEmpty(strValue)){
                longValue=Long.parseLong(strValue);
            }
        }
        return longValue;
    }


    public static int castInt(Object object) {
        return CastUtil.castInt(object,0);
    }

    private static int castInt(Object object, int defaultValue) {
        int intValue=defaultValue;
        if (object!=null){
            String strValue=castString(object);
            if (StringUtil.isNotEmpty(strValue)){
                intValue=Integer.parseInt(strValue);
            }
        }
        return intValue;
    }

    public static boolean castBoolean(Object object) {

        return CastUtil.castBoolean(object,false);
    }

    private static boolean castBoolean(Object object, boolean defaultValue) {
        boolean booleanValue=defaultValue;
        if (object!=null){
            String strValue=castString(object);
            if (StringUtil.isNotEmpty(strValue)){
                booleanValue=Boolean.parseBoolean(strValue);
            }
        }
        return booleanValue;
    }
}
