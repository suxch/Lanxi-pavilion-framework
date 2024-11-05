package cn.com.blueInfo;

import cn.com.blueInfo.business.lottery.ParseLotteryFiles;
import cn.com.blueInfo.business.lottery.entity.*;
import cn.com.blueInfo.business.lottery.mapper.SportsLotteryMapper;
import cn.com.blueInfo.business.lottery.mapper.WelfareLotteryMapper;
import cn.com.blueInfo.utils.entity.DataBaseParam;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2
@SpringBootTest
public class DataBaseParamTest {

    @Autowired
    private DataBaseParam dataBaseParam;

    @Autowired
    private WelfareLotteryMapper welfareLotteryMapper;

    @Autowired
    private SportsLotteryMapper sportsLotteryMapper;

    @Autowired
    private SportsLotteryParam sportsLotteryParam;

    @Autowired
    private WelfareLotteryParam welfareLotteryParam;

    @Test
    public void test() {
        log.info(dataBaseParam.getUrl());
        log.info(dataBaseParam.getUsername());
        log.info(dataBaseParam.getPassword());
        log.info(dataBaseParam.getDriverClassName());

        log.info(dataBaseParam.getDumpPath());
        log.info(dataBaseParam.getHost());
        log.info(dataBaseParam.getPort());
        log.info(dataBaseParam.getDatabase());
    }

    @Test
    public void testBusinessParam() {
        log.info(sportsLotteryParam.getUrl());
        log.info(sportsLotteryParam.getMethod());
        log.info("");
        for (String s_s : sportsLotteryParam.getQuery()) log.info(s_s);
        log.info(welfareLotteryParam.getUrl());
        log.info(welfareLotteryParam.getMethod());
        log.info("");
        for (String w_s : welfareLotteryParam.getQuery()) log.info(w_s);
        log.info("");
        for (Map.Entry<String, String> entry : welfareLotteryParam.getHeader().entrySet()) {
            log.info(entry.getKey());
            log.info(entry.getValue());
        }
    }

    @Test
    public void addData() {
//        WelfareLottery welfareLottery = new WelfareLottery();
//        welfareLottery.setUuid(UUID.randomUUID().toString());
//        welfareLotteryMapper.insert(welfareLottery);

        String welfareFolder = "D:" + File.separator +
                "中国彩票基础数据" + File.separator + "双色球";
        // 双色球返回数据
        JSONArray welfareResultData = ParseLotteryFiles.startParse(welfareFolder);
        saveWelfareData(welfareResultData);

        String sportsFolder = "D:" + File.separator +
                "中国彩票基础数据" + File.separator + "大乐透";
        // 大乐透返回数据
        JSONArray sportsResultData = ParseLotteryFiles.startParse(sportsFolder);
        saveSportsData(sportsResultData);
        System.out.println("断点");
    }

    public void saveWelfareData(JSONArray data) {
        List<WelfareLottery> welfareLotteryList = JSONArray.parseArray(data.toJSONString(), WelfareLottery.class);
        welfareLotteryMapper.delete(null);
        for (WelfareLottery welfareLottery : welfareLotteryList) {
            welfareLottery.setUuid(UUID.randomUUID().toString());
            int addCount = welfareLotteryMapper.insert(welfareLottery);
        }
        List<WelfareLottery_All> welfareLotteryAllList = JSONArray.parseArray(data.toJSONString(), WelfareLottery_All.class);
        for (WelfareLottery_All welfareLotteryAll : welfareLotteryAllList) {
            welfareLotteryAll.setUuid(UUID.randomUUID().toString());
        }
    }

    public void saveSportsData(JSONArray data) {
        List<SportsLottery> sportsLotteryList = JSONArray.parseArray(data.toJSONString(), SportsLottery.class);
        sportsLotteryMapper.delete(null);
        for (SportsLottery sportsLottery : sportsLotteryList) {
            sportsLottery.setUuid(UUID.randomUUID().toString());
            int addCount = sportsLotteryMapper.insert(sportsLottery);
        }
        List<SportsLottery_All> sportsLotteryAllList = JSONArray.parseArray(data.toJSONString(), SportsLottery_All.class);
        for (SportsLottery_All sportsLotteryAll : sportsLotteryAllList) {
            sportsLotteryAll.setUuid(UUID.randomUUID().toString());
        }
    }

}
