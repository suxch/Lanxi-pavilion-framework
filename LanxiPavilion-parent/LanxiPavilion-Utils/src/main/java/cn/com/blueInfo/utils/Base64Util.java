package cn.com.blueInfo.utils;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * @Description: 自定义加密解密工具类
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2018/8/13 20:47
 * @Version: 1.0
 */
public class Base64Util {

    /**
     * @description: 加密方法
     * @author: suxch
     * @date: 2024/8/13 20:54
     * @param encodeStr 需要加密的明文
     * @param privateKey 私钥
     * @return: java.lang.String
     **/
    public String base64Encode(String encodeStr, String privateKey) {
        try {
            byte[] byteArr = this.desEncrypt(encodeStr.getBytes(), privateKey);
            return Base64Utils.encodeToString(byteArr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @description: 解密方法
     * @author: suxch
     * @date: 2024/8/13 20:56
     * @param decodeStr 需要解密的密文
     * @param privateKey 私钥
     * @return: java.lang.String
     **/
    public String base64Decode(String decodeStr, String privateKey) {
        try {
            byte[] byteArr = Base64Utils.decode(decodeStr.getBytes());
            return new String(this.desDecrypt(byteArr, privateKey), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * des 加密
     * @param plainText 明文
     * @param desKeyParameter 加密秘钥
     * @return 二进制字节数组
     */
    private byte[] desEncrypt(byte[] plainText, String desKeyParameter) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = desKeyParameter.getBytes();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        byte data[] = plainText;
        byte encryptedData[] = cipher.doFinal(data);
        return encryptedData;
    }

    /**
     * des 解密
     * @param encryptText 密文
     * @param desKeyParameter 解密秘钥
     * @return 二进制字节数组
     */
    private byte[] desDecrypt(byte[] encryptText, String desKeyParameter) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = desKeyParameter.getBytes();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        byte encryptedData[] = encryptText;
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return decryptedData;
    }

}
