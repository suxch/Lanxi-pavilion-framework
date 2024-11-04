package cn.com.blueInfo.business;

import cn.com.blueInfo.utils.IntegerUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

@Log4j2
@Data
public class TestEntity {
    private String url = "https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry";
    private String method = "get";
    private String[] query = {"pageNo=1","pageSize=30","gameNo=85","provinceId=0","isVerify=1"};

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
    @Test
    public void testEntity() {
        log.info(getUrl(3, 30));
    }

}
