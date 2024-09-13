package cn.com.blueInfo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * @Description: 加密解密工具类
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:01
 * @Version: 1.0
 */
public class EnDecryptUtil {
    private static final Decoder decoder = Base64.getDecoder();
    private static final Encoder encoder = Base64.getEncoder();

    /**
     * MD5加Base64认证码
     * @Title: md5And64ToStr
     * @param expressStr
     * @return String
     * @throws
     */
    public static String MD5And64ToStr(String expressStr) {
        if (expressStr == null || "".equals(expressStr.trim())) {
            return "";
        }
        return encoder.encodeToString(md5Encode(expressStr));
    }

    /**
     * Base64加密
     * @Title: base64Encode
     * @param expressStr
     * @return String
     * @throws
     */
    public static String Base64Encode(String expressStr) {
        String cipherStr = "";
        try {
            byte[] byteStr = expressStr.getBytes("UTF-8");
            cipherStr = encoder.encodeToString(byteStr);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return cipherStr;
    }

    /**
     * Base64解密
     * @Title: base64Decode
     * @param cipherStr
     * @return String
     * @throws
     */
    public static String Base64Decode(String cipherStr) {
        String expressStr = "";
        try {
            expressStr = new String(decoder.decode(cipherStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return expressStr;
    }

    /**
     * MD5加密
     * @Title: md5Encode
     * @param expressStr
     * @return byte[]
     * @throws
     */
    private static byte[] md5Encode(String expressStr) {
        byte[] output = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] input = expressStr.getBytes();
            output = md5.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

}
