package cn.com.blueInfo.utils;

public class IntegerUtils {

    public static boolean isEmpty (Integer num) {
        return num == null || num == 0;
    }

    public static boolean isNotEmpty (Integer num) {
        return num != null && num != 0;
    }

}
