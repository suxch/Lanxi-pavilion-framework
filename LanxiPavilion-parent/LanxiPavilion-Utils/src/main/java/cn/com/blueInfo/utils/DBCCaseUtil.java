package cn.com.blueInfo.utils;

/**
 * @Description: 全角半角字符切换工具
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2024/8/13 21:50
 * @Version: 1.0
 */
public class DBCCaseUtil {

    /**
     * @description: 全角字符转半角字符
     * @author: suxch
     * @date: 2024/8/13 21:50
     * @param: [input]
     * @return: java.lang.String
     **/
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                // 全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                // 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * @description: 半角字符转全角字符
     * @author: suxch
     * @date: 2024/8/13 21:51
     * @param: [input]
     * @return: java.lang.String
     **/
    public static String ToSBC(String input) {
        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127) {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

}
