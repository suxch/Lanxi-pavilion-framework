package cn.com.blueInfo.utils.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.entity
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:57
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FTPParam {

    private String host;

    private Integer port;

    private String userName;

    private String password;

    private String path;

    private String appPath;

}
