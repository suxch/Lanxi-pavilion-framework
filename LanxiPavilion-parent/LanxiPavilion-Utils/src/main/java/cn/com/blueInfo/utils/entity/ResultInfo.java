package cn.com.blueInfo.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description: TODO
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.result
 * @Author: suxch
 * @CreateTime: 2024/8/15 17:11
 * @Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo {

    /** 返回的状态 */
    private boolean result = true;
    /** 返回的消息 */
    private String message = "数据获取成功";
    /** 返回的数据 */
    private Object data;
    /** 返回的异常信息 */
    private String error;

}
