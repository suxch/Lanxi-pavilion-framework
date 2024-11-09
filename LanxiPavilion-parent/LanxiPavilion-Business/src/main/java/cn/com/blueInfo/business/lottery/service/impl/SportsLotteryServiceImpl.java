package cn.com.blueInfo.business.lottery.service.impl;

import cn.com.blueInfo.business.lottery.entity.SportsLottery;
import cn.com.blueInfo.business.lottery.entity.SportsLotteryParam;
import cn.com.blueInfo.business.lottery.mapper.SportsLotteryMapper;
import cn.com.blueInfo.business.lottery.service.SportsLotteryService;
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
public class SportsLotteryServiceImpl implements SportsLotteryService {

    @Autowired
    private SportsLotteryMapper sportsLotteryMapper;

    @Autowired
    private SportsLotteryParam sportsLotteryParam;

    @Override
    public void addSportsLotteryDataForHttp() {
        sportsLotteryMapper.delete(null);
        String result = HttpClient.doGet(sportsLotteryParam.getUrl(1, 30), sportsLotteryParam.getHeader());
        JSONObject resultData = JSON.parseObject(result);
        JSONObject resultValue = resultData.getJSONObject("value");
        log.info(resultData);
        addDataToDatabase(resultValue);
        Integer pages = resultValue.getInteger("pages");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int p_i = 2; p_i <= pages; p_i++) {
            String onePageResult = HttpClient.doGet(sportsLotteryParam.getUrl(p_i, 30), sportsLotteryParam.getHeader());
            JSONObject onePageResultData = JSON.parseObject(onePageResult);
            JSONObject onePageResultValue = onePageResultData.getJSONObject("value");
            log.info(onePageResultData);
            addDataToDatabase(onePageResultValue);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addDataToDatabase(JSONObject resultData) {
        JSONArray data = resultData.getJSONArray("list");
        for (int d_i = 0, d_len = data.size(); d_i < d_len; d_i++) {
            JSONObject oneData = data.getJSONObject(d_i);
            SportsLottery sportsLottery = new SportsLottery();

            sportsLottery.setUuid(UUID.randomUUID().toString());
            sportsLottery.setIssue(oneData.getString("lotteryDrawNum"));
            sportsLottery.setDate(oneData.getString("lotteryDrawTime"));

            String lotteryDrawResult = oneData.getString("lotteryDrawResult");
            String[] lotteryBalls = lotteryDrawResult.split(" ");
            sportsLottery.setRed1(lotteryBalls[0]);
            sportsLottery.setRed2(lotteryBalls[1]);
            sportsLottery.setRed3(lotteryBalls[2]);
            sportsLottery.setRed4(lotteryBalls[3]);
            sportsLottery.setRed5(lotteryBalls[4]);
            sportsLottery.setBlue1(lotteryBalls[5]);
            sportsLottery.setBlue2(lotteryBalls[6]);

            StringBuilder lotteryInfo = new StringBuilder();
            for (int l_i = 0, l_len = lotteryBalls.length; l_i < l_len; l_i++) {
                if (l_i == 5) {
                    lotteryInfo.append("--");
                }
                if (l_i < 5) {
                    lotteryInfo.append(lotteryBalls[l_i]).append("-");
                } else {
                    lotteryInfo.append(lotteryBalls[l_i]).append("-");
                }
            }
            lotteryInfo.setLength(lotteryInfo.length() - 1);
            sportsLottery.setLotteryInfo(lotteryInfo.toString());

            JSONArray prizeInfo = oneData.getJSONArray("prizeLevelList");
            for (int p_i = 0, p_len = prizeInfo.size(); p_i < p_len; p_i++) {
                JSONObject onePrize = prizeInfo.getJSONObject(p_i);
                Integer prizeType = onePrize.getInteger("sort");
                if (prizeType == 101) {
                    sportsLottery.setFirstBaseNum(onePrize.getString("stakeCount"));
                    sportsLottery.setFirstBaseMoney(onePrize.getString("stakeAmountFormat"));
                }
                if (prizeType == 201) {
                    sportsLottery.setFirstAppendNum(onePrize.getString("stakeCount"));
                    sportsLottery.setFirstAppendMoney(onePrize.getString("stakeAmountFormat"));
                }
                if (prizeType == 301) {
                    sportsLottery.setSecondBaseNum(onePrize.getString("stakeCount"));
                    sportsLottery.setSecondBaseMoney(onePrize.getString("stakeAmountFormat"));
                }
                if (prizeType == 401) {
                    sportsLottery.setSecondAppendNum(onePrize.getString("stakeCount"));
                    sportsLottery.setSecondAppendMoney(onePrize.getString("stakeAmountFormat"));
                }
            }
            log.info(sportsLottery.toString());
            sportsLotteryMapper.insert(sportsLottery);

        }
    }

}
