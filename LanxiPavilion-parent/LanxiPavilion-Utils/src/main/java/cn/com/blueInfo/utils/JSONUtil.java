package cn.com.blueInfo.utils;

import com.alibaba.fastjson.JSON;

/**
 * @Description: JSON工具类
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:14
 * @Version: 1.0
 */
public class JSONUtil {

    /**
     * 是否能转成JSONObject
     * @Title: isToJSONObjcet
     * @param str
     * @return boolean
     * @throws
     */
    public static boolean isToJSONObject(String str) {
        try {
            JSON.parseObject(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否能转成JSONArray
     * @Title: isToJSONArray
     * @param str
     * @return boolean
     * @throws
     */
    public static boolean isToJSONArray(String str) {
        try {
            JSON.parseArray(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
