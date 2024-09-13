package cn.com.blueInfo.utils.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @Description: TODO
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.result
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:36
 * @Version: 1.0
 */
@Data
public class BootstrapTable {

    /** 数量 */
    private Integer total;
    /** 内容 */
    private List<?> rows;

    @Override
    public String toString() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("total", total);
        resultJson.put("rows", rows);
        return resultJson.toJSONString();
    }

}
