package cn.com.blueInfo.utils.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库config参数
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DataBaseParam {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private String dumpPath;

    public String getHost() {
        String[] arr = url.split(":");
        String result = "";
        if (driverClassName.contains("mysql") || driverClassName.contains("sqlserver")) {
            result = arr[2].replace("/", "");
        } else if (driverClassName.contains("oracle")) {
            result = arr[3].replace("@", "");
        }
        return result;
    }

    public String getPort() {
        String[] arr = url.split(":");
        String result = "";
        if (driverClassName.contains("mysql")) {
            String str = arr[3];
            result = str.substring(0, str.indexOf("/"));
        } else if (driverClassName.contains("sqlserver")) {
            String str = arr[3];
            result = str.substring(0, str.indexOf(";"));
        } else if (driverClassName.contains("oracle")) {
            result = arr[4];
        }
        return result;
    }

    public String getDatabase() {
        String[] arr = url.split("/");
        String result = "";
        if (driverClassName.contains("mysql")) {
            String str = arr[arr.length - 1];
            result = str.substring(0, str.indexOf("?"));
        } else if (driverClassName.contains("sqlserver")) {
            String str = arr[arr.length - 1];
            str = str.substring(str.indexOf("=") + 1);
            result = str.replace(";", "");
        } else if (driverClassName.contains("oracle")) {
            String[] strArr = url.split(":");
            result = strArr[strArr.length - 1];
        }
        return result;
    }

}
