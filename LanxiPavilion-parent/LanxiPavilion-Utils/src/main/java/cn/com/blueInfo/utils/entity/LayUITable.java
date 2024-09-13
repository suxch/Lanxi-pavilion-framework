package cn.com.blueInfo.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.result
 * @Author: suxch
 * @CreateTime: 2024/8/15 17:10
 * @Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LayUITable {

    /** 数据表数量 */
    private String count;
    /** 信息 */
    private String msg = "";
    /** code */
    private String code = "";
    /** 数据表数据 */
    private List<?> data;

}
