package cn.com.blueInfo.utils;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MinecraftDemo {

    private String minecraft;

    @Test
    public void testMap() {
        JSONObject json = new JSONObject();
        json.put("suxch", "苏希辰");
        String requestParam = "{\"suxch\": \"苏希辰\"}";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("1", json);
        Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
        for (Map.Entry<String, Object> mapEntry : entrySet) {
            System.out.println(mapEntry.getKey());
            System.out.println(mapEntry.getValue().toString());
        }
    }

}

/**
 * 基础材料
 */
class BaseMaterials {

}