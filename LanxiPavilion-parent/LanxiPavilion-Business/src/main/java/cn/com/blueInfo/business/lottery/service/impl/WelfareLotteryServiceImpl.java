package cn.com.blueInfo.business.lottery.service.impl;

import cn.com.blueInfo.business.lottery.entity.WelfareLottery;
import cn.com.blueInfo.business.lottery.entity.WelfareLotteryParam;
import cn.com.blueInfo.business.lottery.mapper.WelfareLotteryMapper;
import cn.com.blueInfo.business.lottery.service.WelfareLotteryService;
import cn.com.blueInfo.utils.client.HttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class WelfareLotteryServiceImpl implements WelfareLotteryService {

    @Autowired
    private WelfareLotteryMapper welfareLotteryMapper;

    @Autowired
    private WelfareLotteryParam welfareLotteryParam;

    @Override
    public void addWelfareLotteryDataForHttp() {
        welfareLotteryMapper.delete(null);
        String result = HttpClient.doGet(welfareLotteryParam.getUrl(1, 30), welfareLotteryParam.getHeader());
        JSONObject resultData = JSON.parseObject(result);
        log.info(resultData);
        addDataToDatabase(resultData);
        Integer pageNum = resultData.getInteger("pageNum");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int p_i = 2; p_i <= pageNum; p_i++) {
            String onePageResult = HttpClient.doGet(welfareLotteryParam.getUrl(p_i, 30), welfareLotteryParam.getHeader());
            JSONObject onePageResultData = JSON.parseObject(onePageResult);
            log.info(onePageResultData);
            addDataToDatabase(onePageResultData);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addDataToDatabase(JSONObject resultData) {
        JSONArray data = resultData.getJSONArray("result");
        for (int d_i = 0, d_len = data.size(); d_i < d_len; d_i++) {
            JSONObject oneData = data.getJSONObject(d_i);
            WelfareLottery welfareLottery = new WelfareLottery();

            welfareLottery.setUuid(UUID.randomUUID().toString());
            welfareLottery.setIssue(oneData.getString("code"));
            welfareLottery.setDate(oneData.getString("date"));

            String red = oneData.getString("red");
            String[] redBalls = red.split(",");
            welfareLottery.setRed1(redBalls[0]);
            welfareLottery.setRed2(redBalls[1]);
            welfareLottery.setRed3(redBalls[2]);
            welfareLottery.setRed4(redBalls[3]);
            welfareLottery.setRed5(redBalls[4]);
            welfareLottery.setRed6(redBalls[5]);

            String blueBall = oneData.getString("blue");
            welfareLottery.setBlue(blueBall);

            StringBuilder lotteryInfo = new StringBuilder();
            for (String redBall : redBalls) {
                lotteryInfo.append(redBall).append("-");
            }
            lotteryInfo.append("--").append(blueBall);
            welfareLottery.setLottery_info(lotteryInfo.toString());

            JSONArray prizeInfo = oneData.getJSONArray("prizegrades");
            for (int p_i = 0, p_len = prizeInfo.size(); p_i < p_len; p_i++) {
                JSONObject onePrize = prizeInfo.getJSONObject(p_i);
                Integer prizeType = onePrize.getInteger("type");
                if (prizeType == 1) {
                    welfareLottery.setFirst_num(onePrize.getString("typenum"));
                    welfareLottery.setFirst_money(onePrize.getString("typemoney"));
                }
                if (prizeType == 2) {
                    welfareLottery.setSecond_num(onePrize.getString("typenum"));
                    welfareLottery.setSecond_money(onePrize.getString("typemoney"));
                }
            }
            log.info(welfareLottery.toString());
            welfareLotteryMapper.insert(welfareLottery);
        }
    }
}
