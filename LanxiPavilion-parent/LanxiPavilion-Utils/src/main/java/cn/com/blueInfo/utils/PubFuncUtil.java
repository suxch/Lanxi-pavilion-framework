package cn.com.blueInfo.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * @Description: 公共方法工具
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2024/8/16 16:43
 * @Version: 1.0
 */
public class PubFuncUtil {

    public static void closeResource(Object... objectArr) {
        try {
            for (Object oneObject : objectArr) {
                if (oneObject instanceof String) {
                    System.out.println(oneObject);
                } else if (oneObject instanceof CloseableHttpResponse) {
                    ((CloseableHttpResponse) oneObject).close();
                } else if (oneObject instanceof CloseableHttpClient) {
                    ((CloseableHttpClient) oneObject).close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
