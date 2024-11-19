package cn.com.blueInfo.business.lottery.service.impl;

import cn.com.blueInfo.business.lottery.entity.WelfareLottery;
import cn.com.blueInfo.business.lottery.entity.WelfareLotteryParam;
import cn.com.blueInfo.business.lottery.mapper.WelfareLotteryMapper;
import cn.com.blueInfo.business.lottery.service.WelfareLotteryService;
import cn.com.blueInfo.business.lottery.util.LotteryUtils;
import cn.com.blueInfo.utils.client.HttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class WelfareLotteryServiceImpl extends ServiceImpl<WelfareLotteryMapper, WelfareLottery>
        implements WelfareLotteryService {

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

    @Override
    public void createWelfareLotteryData() {

    }

    @Override
    public void updateLatestData() {
        LambdaQueryWrapper<WelfareLottery> wrapper = new LambdaQueryWrapper<WelfareLottery>()
                .select(WelfareLottery::getUuid, WelfareLottery::getIssue, WelfareLottery::getDate)
                .orderByDesc(WelfareLottery::getDate);
//        List<WelfareLottery> welfareLotteryList = welfareLotteryMapper.selectList(wrapper);

        IPage<WelfareLottery> pageParam = new Page<WelfareLottery>();
        pageParam.setCurrent(1);
        pageParam.setSize(10);

        IPage<WelfareLottery> welfareLotteryIPage = welfareLotteryMapper.selectPage(pageParam, wrapper);
        log.info(welfareLotteryIPage.getRecords().size());
    }

    @Override
    public void createLotteryInfo() {
        List<List<Integer>> redBallList = LotteryUtils.generateCombinations(33, 6);
        Collections.shuffle(redBallList);
        List<List<Integer>> blueBallList = LotteryUtils.generateCombinations(16, 1);
        Collections.shuffle(blueBallList);

        String tableName = "welfare_lottery";
        List<WelfareLottery> welfareLotteryList = new ArrayList<>();
        for (int r_i = 0, r_len = redBallList.size(); r_i < r_len; r_i++) {
            List<Integer> redBall = redBallList.get(r_i);
            for (List<Integer> blueBall : blueBallList) {
                welfareLotteryList.add(batchSaveCreateLotteryInfo(redBall, blueBall));
            }
            // 第一次存储的时候需要创建表
            if (r_i % 50000 == 0) {
                tableName = getTableNameCount(tableName);
                baseMapper.createWelfareSubTable(tableName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (r_i != 0) {
                    baseMapper.updateAutoIncrement(tableName, "" + ((r_i * 16) + 1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            int insertCount = baseMapper.customBatchInsert(tableName, welfareLotteryList);
            if (insertCount != 0) {
                welfareLotteryList.clear();
            }
        }
//        for (List<Integer> redBall : redBallList) {
//            for (List<Integer> blueBall : blueBallList) {
//                welfareLotteryList.add(batchSaveCreateLotteryInfo(redBall, blueBall));
//            }
//            if (saveBatch(welfareLotteryList)) {
//                welfareLotteryList.clear();
////                try {
////                    Thread.sleep(100);
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                }
//            }
//        }
    }

    private String getTableNameCount(String tableName) {
        String result = "";
        String[] names = tableName.split("_");
        if (names.length == 2) {
            result = tableName + "_01";
        } else {
            int tableCount = Integer.parseInt(names[2]);
            tableCount++;
            result = tableName.substring(0, tableName.length() - 2)
                    + (String.valueOf(tableCount).length() == 1 ? "0" + tableCount : "" + tableCount);
        }
        return result;
    }

    private WelfareLottery batchSaveCreateLotteryInfo(List<Integer> redBall, List<Integer> blueBall) {
        WelfareLottery welfareLottery = new WelfareLottery();
        welfareLottery.setUuid(UUID.randomUUID().toString());
        welfareLottery.setRed1(number2String(redBall.get(0)));
        welfareLottery.setRed2(number2String(redBall.get(1)));
        welfareLottery.setRed3(number2String(redBall.get(2)));
        welfareLottery.setRed4(number2String(redBall.get(3)));
        welfareLottery.setRed5(number2String(redBall.get(4)));
        welfareLottery.setRed6(number2String(redBall.get(5)));
        welfareLottery.setBlue(number2String(blueBall.get(0)));
        StringBuilder lotteryInfo = new StringBuilder();
        for (Integer num : redBall) {
            lotteryInfo.append(number2String(num)).append("-");
        }
        lotteryInfo.append("--").append(number2String(blueBall.get(0)));
        welfareLottery.setLotteryInfo(lotteryInfo.toString());
        return welfareLottery;
    }

    private String number2String(Integer number) {
        return String.valueOf(number).length() == 1 ? "0" + number : number + "";
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
            welfareLottery.setLotteryInfo(lotteryInfo.toString());

            JSONArray prizeInfo = oneData.getJSONArray("prizegrades");
            for (int p_i = 0, p_len = prizeInfo.size(); p_i < p_len; p_i++) {
                JSONObject onePrize = prizeInfo.getJSONObject(p_i);
                Integer prizeType = onePrize.getInteger("type");
                if (prizeType == 1) {
                    welfareLottery.setFirstNum(onePrize.getString("typenum"));
                    welfareLottery.setFirstMoney(onePrize.getString("typemoney"));
                }
                if (prizeType == 2) {
                    welfareLottery.setSecondNum(onePrize.getString("typenum"));
                    welfareLottery.setSecondMoney(onePrize.getString("typemoney"));
                }
            }
            log.info(welfareLottery.toString());
            welfareLotteryMapper.insert(welfareLottery);
        }
    }
}
