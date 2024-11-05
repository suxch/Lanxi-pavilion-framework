package cn.com.blueInfo.business.lottery.entity;

import cn.com.blueInfo.config.YamlFileLoader;
import cn.com.blueInfo.utils.IntegerUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Component
@PropertySource(value = "classpath:config/business.yml", factory = YamlFileLoader.class)
@ConfigurationProperties(prefix = "business.lottery.welfare")
public class WelfareLotteryParam {

    private String url;
    private String method;
    private String[] query;
    private String[] header;

    public String getUrl(Integer pageNo, Integer pageSize) {
        String result = url;
        result = result.concat("?");
        for (int q_i = 0, q_len = getQuery().length; q_i < q_len; q_i++) {
            if (q_i == 0 && IntegerUtils.isNotEmpty(pageNo)) {
                String param = getQuery()[q_i];
                param = param.substring(0, param.indexOf("=") + 1);
                param = param.concat(String.valueOf(pageNo));
                result = result.concat(param).concat("&");
            } else if (q_i == 1 && IntegerUtils.isNotEmpty(pageSize)) {
                String param = getQuery()[q_i];
                param = param.substring(0, param.indexOf("=") + 1);
                param = param.concat(String.valueOf(pageSize));
                result = result.concat(param).concat("&");
            } else {
                result = result.concat(getQuery()[q_i]).concat("&");
            }
        }
        return result.substring(0, result.length() - 1);
    }

    public Map<String, String> getHeader() {
        Map<String, String> result = new LinkedHashMap<>();
        for (String oneHeader : header) {
            String[] headerInfo = oneHeader.split(":");
            if (headerInfo.length > 2) {
                result.put(oneHeader.substring(0, oneHeader.indexOf(":")), oneHeader.substring(oneHeader.indexOf(":") + 1));
            } else {
                result.put(headerInfo[0], headerInfo[1]);
            }
        }
        return result;
    }

}
