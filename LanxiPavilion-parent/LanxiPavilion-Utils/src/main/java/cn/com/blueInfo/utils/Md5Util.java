package cn.com.blueInfo.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * @Description: MD5加密工具类
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:17
 * @Version: 1.0
 */
public class Md5Util {

    public static String md5(String expressStr) {

        ResultMd5 result = new ResultMd5();

        if (expressStr == null || "".equals(expressStr.trim())) {
            result.setResult(false);
            result.setError("明文为空");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] input = expressStr.getBytes();
            byte[] output = digest.digest(input);
            // System.out.println(output.length);
            // 采用Base64算法将加密后的字节数组转成字符串
            String ciphertextStr = Base64.encodeBase64String(output);
            result.setCiphertextStr(ciphertextStr);
        } catch (Exception e) {
            result.setResult(false);
            result.setError(e.getMessage());
            throw new RuntimeException(e);
        }
        return JSON.toJSONString(result);
    }
}

/**
 * MD5返回信息实体类
 * @ClassName: resultMd5
 * @author suxch
 * @date 2018年3月9日  上午10:18:49
 */
@Data
class ResultMd5 {

    /** 返回状态 */
    private boolean result = true;
    /** 密文字符串 */
    private String ciphertextStr;
    /** 异常信息 */
    private String error;

}