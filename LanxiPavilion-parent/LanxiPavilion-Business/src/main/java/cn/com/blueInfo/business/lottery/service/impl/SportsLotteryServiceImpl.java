package cn.com.blueInfo.business.lottery.service.impl;

import cn.com.blueInfo.business.lottery.entity.SportsLottery;
import cn.com.blueInfo.business.lottery.entity.SportsLotteryParam;
import cn.com.blueInfo.business.lottery.mapper.SportsLotteryMapper;
import cn.com.blueInfo.business.lottery.service.SportsLotteryService;
import cn.com.blueInfo.business.lottery.util.LotteryUtils;
import cn.com.blueInfo.utils.client.HttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class SportsLotteryServiceImpl extends ServiceImpl<SportsLotteryMapper, SportsLottery>
        implements SportsLotteryService {

    @Autowired
    private SportsLotteryParam sportsLotteryParam;

    @Override
    public void addSportsLotteryDataForHttp() {
        baseMapper.delete(null);
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

    @Override
    public void createLotteryInfo() {
        List<List<Integer>> redBallList = LotteryUtils.generateCombinations(35, 5);
        List<List<Integer>> blueBallList = LotteryUtils.generateCombinations(12, 2);

        createLotteryInfo(redBallList, blueBallList, "sports_lottery_0");

        Collections.shuffle(redBallList);
        Collections.shuffle(blueBallList);

        createLotteryInfo(redBallList, blueBallList, "sports_lottery_1");

        createLotteryInfo2(redBallList, blueBallList);

    }

    private void createLotteryInfo(List<List<Integer>> redBallList, List<List<Integer>> blueBallList,
                                   String tableName) {
        List<SportsLottery> sportsLotteryList = new ArrayList<>();
        for (int r_i = 0, r_len = redBallList.size(); r_i < r_len; r_i++) {
            List<Integer> redBall = redBallList.get(r_i);
            for (List<Integer> blueBall : blueBallList) {
                sportsLotteryList.add(batchSaveCreateLotteryInfo(redBall, blueBall));
            }
            // 第一次存储的时候需要创建表
            if (r_i % 15000 == 0) {
                tableName = LotteryUtils.getTableNameCount(tableName, 3);
                baseMapper.createSportsSubTable(tableName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (r_i != 0) {
                    baseMapper.updateAutoIncrement(tableName, "" + ((r_i * 66) + 1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            int insertCount = baseMapper.customBatchInsert(tableName, sportsLotteryList);
            if (insertCount != 0) {
                sportsLotteryList.clear();
            }
        }
    }

    private void createLotteryInfo2(List<List<Integer>> redBallList, List<List<Integer>> blueBallList) {
        String tableName = "sports_lottery_2";
        List<String> lotteryInfoList = LotteryUtils.randomLotteryInfo(redBallList, blueBallList);

        List<SportsLottery> sportsLotteryList = new ArrayList<>();
        for (int l_i = 0, l_len = lotteryInfoList.size(); l_i < l_len; l_i++) {
            int index = l_i + 1;
            String lotteryInfo = lotteryInfoList.get(l_i);
            sportsLotteryList.add(createSportsLotteryInfo(lotteryInfo));

            // 第一次存储的时候需要创建表
            if (l_i % 990000 == 0) {
                tableName = LotteryUtils.getTableNameCount(tableName, 3);
                baseMapper.createSportsSubTable(tableName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (l_i != 0) {
                    baseMapper.updateAutoIncrement(tableName, "" + (l_i + 1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (index % 80 == 0 || l_i == l_len - 1) {
                int insertCount = baseMapper.customBatchInsert(tableName, sportsLotteryList);
                if (insertCount != 0) {
                    sportsLotteryList.clear();
                }
            }
        }

    }

    private SportsLottery batchSaveCreateLotteryInfo(List<Integer> redBall, List<Integer> blueBall) {
        SportsLottery sportsLottery = new SportsLottery();
        sportsLottery.setUuid(UUID.randomUUID().toString());
        sportsLottery.setRed1(LotteryUtils.number2String(redBall.get(0)));
        sportsLottery.setRed2(LotteryUtils.number2String(redBall.get(1)));
        sportsLottery.setRed3(LotteryUtils.number2String(redBall.get(2)));
        sportsLottery.setRed4(LotteryUtils.number2String(redBall.get(3)));
        sportsLottery.setRed5(LotteryUtils.number2String(redBall.get(4)));
        sportsLottery.setBlue1(LotteryUtils.number2String(blueBall.get(0)));
        sportsLottery.setBlue2(LotteryUtils.number2String(blueBall.get(1)));
        sportsLottery.setLotteryInfo(LotteryUtils.getCreateLotteryInfo(redBall, blueBall));
        return sportsLottery;
    }

    private SportsLottery createSportsLotteryInfo(String lotteryInfo) {
        SportsLottery sportsLottery = new SportsLottery();
        sportsLottery.setUuid(UUID.randomUUID().toString());
        String[] lotteryInfoArray = lotteryInfo.split("---");
        String redBall = lotteryInfoArray[0];
        String blueBall = lotteryInfoArray[1];
        String[] redBallArray = redBall.split("-");
        String[] blueBallArray = blueBall.split("-");
        sportsLottery.setRed1(LotteryUtils.number2String(redBallArray[0]));
        sportsLottery.setRed2(LotteryUtils.number2String(redBallArray[1]));
        sportsLottery.setRed3(LotteryUtils.number2String(redBallArray[2]));
        sportsLottery.setRed4(LotteryUtils.number2String(redBallArray[3]));
        sportsLottery.setRed5(LotteryUtils.number2String(redBallArray[4]));
        sportsLottery.setBlue1(LotteryUtils.number2String(blueBallArray[0]));
        sportsLottery.setBlue2(LotteryUtils.number2String(blueBallArray[1]));
        sportsLottery.setLotteryInfo(lotteryInfo);
        return sportsLottery;
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
            baseMapper.insert(sportsLottery);

        }
    }

}
