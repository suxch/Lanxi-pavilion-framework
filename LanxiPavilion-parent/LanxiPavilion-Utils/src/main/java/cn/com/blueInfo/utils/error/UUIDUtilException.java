package cn.com.blueInfo.utils.error;

/**
 * @Description: UUID工具类异常处理
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util.error
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:24
 * @Version: 1.0
 */
public class UUIDUtilException extends Exception {

    /**
     *
     * @Title: UUIDUtilException.java
     * @Package cn.com.suxch.framework.util.error
     * @author suxch
     * @date 2018年12月19日  上午12:31:06
     * @version V1.0
     */
    private static final long serialVersionUID = 1746184773980066937L;

    /**
     *
     */
    public UUIDUtilException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public UUIDUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public UUIDUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public UUIDUtilException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public UUIDUtilException(Throwable cause) {
        super(cause);
    }

}
