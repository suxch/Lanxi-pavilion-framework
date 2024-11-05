package cn.com.blueInfo.business;

import cn.com.blueInfo.utils.IntegerUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@Data
public class TestEntity {
    private String url = "https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry";
    private String method = "get";
    private String[] query = {"pageNo=1","pageSize=30","gameNo=85","provinceId=0","isVerify=1"};
    private String[] header = {
            "authority:webapi.sporttery.cn",
            "method:GET",
            "path:/gateway/lottery/getHistoryPageListV1.qry?gameNo=85&provinceId=0&pageSize=30&isVerify=1&pageNo=56",
            "scheme:https",
            "accept:application/json, text/javascript, */*; q=0.01",
            "accept-encoding:gzip, deflate, br, zstd",
            "accept-language:zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
            "origin:https://static.sporttery.cn",
            "priority:u=1, i",
            "referer:https://static.sporttery.cn/",
            "sec-ch-ua:\"Chromium\";v=\"130\", \"Microsoft Edge\";v=\"130\", \"Not?A_Brand\";v=\"99\"",
            "sec-ch-ua-mobile:?0",
            "sec-ch-ua-platform:\"Windows\"",
            "sec-fetch-dest:empty",
            "sec-fetch-mode:cors",
            "sec-fetch-site:same-site",
            "user-agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36 Edg/130.0.0.0"
    };

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

    @Test
    public void testEntity() {
        log.info(getHeader());
    }

}
