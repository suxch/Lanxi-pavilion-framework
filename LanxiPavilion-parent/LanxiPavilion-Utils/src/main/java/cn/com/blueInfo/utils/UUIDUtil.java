package cn.com.blueInfo.utils;

import cn.com.blueInfo.utils.error.UUIDUtilException;

import java.util.UUID;

/**
 * @Description: TODO
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:23
 * @Version: 1.0
 */
public class UUIDUtil {

    /**
     * 创建流程实例ID
     * @Title: createPiid
     * @return String
     * @throws
     */
    public static String createPiid() {
        UUID unid = UUID.randomUUID();
        String unidStr = "piid:" + unid.toString() + "-" + System.currentTimeMillis();
        return unidStr;
    }

    /**
     * 创建任务实例ID
     * @Title: createWiid
     * @return String
     * @throws
     */
    public static String createWiid() {
        UUID unid = UUID.randomUUID();
        String unidStr = "wiid:" + unid.toString() + "-" + System.currentTimeMillis();
        return unidStr;
    }

    /**
     * 创建工单号
     * @Title: createApplyId
     * @param businessProcess 业务流程编号
     * @throws UUIDUtilException
     * @return String
     * @throws
     */
    public static String createApplyId(String businessProcess) throws UUIDUtilException {
        if (businessProcess == null || "".equals(businessProcess)) {
            throw new UUIDUtilException("业务流程编号不能为空或为null");
        }
        UUID unid = UUID.randomUUID();
        String unidStr = businessProcess + ":" + unid.toString() + "-" + System.currentTimeMillis();
        return unidStr;
    }

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
