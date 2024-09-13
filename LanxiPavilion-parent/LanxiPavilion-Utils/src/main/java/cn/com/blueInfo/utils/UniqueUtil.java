package cn.com.blueInfo.utils;

import java.util.UUID;

/**
 * @Description: 唯一值工具类
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:21
 * @Version: 1.0
 */
public class UniqueUtil {

    /**
     * 创建UNID
     * @Title: createUNID
     * @return String
     * @throws
     */
    public static String createUNID() {
        UUID unid = UUID.randomUUID();
        String unidStr = "_DID" + unid.toString();
        return unidStr;
    }

    /**
     * 创建UUID
     * @Title: createUUID
     * @return String
     * @throws
     */
    public static String createUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
