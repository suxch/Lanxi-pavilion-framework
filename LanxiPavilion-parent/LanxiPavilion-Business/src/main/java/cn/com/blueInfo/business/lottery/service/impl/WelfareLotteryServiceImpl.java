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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class WelfareLotteryServiceImpl extends ServiceImpl<WelfareLotteryMapper, WelfareLottery>
        implements WelfareLotteryService {

    @Autowired
    private WelfareLotteryParam welfareLotteryParam;

    @Override
    public JSONArray queryLotteryInfoByString(String lotteryInfo) {
        String tableName2 = "welfare_lottery_";
        JSONArray resultInfo = new JSONArray();
        for (int t_1 = 0, t1_len = 3; t_1 < t1_len; t_1++) {
            String tableName1 = tableName2.concat(String.valueOf(t_1)).concat("_");
            for (int t_2 = 1, t2_len = 23; t_2 <= t2_len; t_2++) {
                String tableName = tableName1.concat(LotteryUtils.number2String(t_2));
                Integer str = baseMapper.queryLotteryIdByInfoForTable(tableName, lotteryInfo);
                if (str != null) {
                    JSONObject result = new JSONObject();
                    result.put("lotteryId", str);
                    result.put("tableName", tableName);
                    resultInfo.add(result);
                }
            }
        }
        return resultInfo;
    }

    @Override
    public List<String> queryWelfareLotteryInfo(String birthday) {
        Long counted = lambdaQuery().count();
        Random random = new Random();
        int randomNum = 0;
        if (StringUtils.isEmpty(birthday)) birthday = "17721088";
        for (int i = 0, len = (int) (counted + 1); i < len; i++) {
            int a = random.nextInt(Integer.parseInt(birthday));
            if (i < len - 1) {
                randomNum = a;
            }
        }
        String tableNum = String.valueOf((randomNum / 800000) + 1);
        tableNum = LotteryUtils.number2String(tableNum);

        List<String> resultList = new ArrayList<String>();

        for (int i = 0, len = 3; i < len; i++) {
            String tableName = "welfare_lottery_" + i + "_" + tableNum;
            resultList.add(baseMapper.queryLotteryInfoByIdForTable(tableName, randomNum));
        }

        resultList.forEach(System.out::println);

        return resultList;

    }

    @Override
    public void addWelfareLotteryDataForHttp() {
        addWelfareLotteryDataForHttp(null);
    }


    public void addWelfareLotteryDataForHttp(String lastIssue) {
        if (lastIssue == null) {
            baseMapper.createWelfareSubTable("welfare_lottery");
            baseMapper.delete(null);
        }
        String result = HttpClient.doGet(welfareLotteryParam.getUrl(1, 30), welfareLotteryParam.getHeader());
        JSONObject resultData = JSON.parseObject(result);
        log.info(resultData);
        addDataToDatabase(resultData, lastIssue);
        if (lastIssue != null) {
            return;
        }
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
            addDataToDatabase(onePageResultData, null);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateLatestLotteryDataForHttp() {
        LambdaQueryWrapper<WelfareLottery> wrapper = new LambdaQueryWrapper<WelfareLottery>()
                .select(WelfareLottery::getUuid, WelfareLottery::getIssue, WelfareLottery::getDate)
                .orderByDesc(WelfareLottery::getDate);

        Page<WelfareLottery> welfareLotteryPage = Page.of(1, 1);

        Page<WelfareLottery> welfareLotteryIPage = page(welfareLotteryPage, wrapper);

        log.info(welfareLotteryIPage.getRecords().get(0).getIssue());

        Long counted = lambdaQuery().count();

        log.info(counted);

        String lastIssue = welfareLotteryIPage.getRecords().get(0).getIssue();
        addWelfareLotteryDataForHttp(lastIssue);

        Long counted1 = lambdaQuery().count();

        log.info("共增加了" + (counted1 - counted) + "条数据");

    }

    @Override
    public void createLotteryInfo() {
        List<List<Integer>> redBallList = LotteryUtils.generateCombinations(33, 6);
        List<List<Integer>> blueBallList = LotteryUtils.generateCombinations(16, 1);

        createLotteryInfo(redBallList, blueBallList, "welfare_lottery_0");

        Collections.shuffle(redBallList);
        Collections.shuffle(blueBallList);

        createLotteryInfo(redBallList, blueBallList, "welfare_lottery_1");

        createLotteryInfo1(redBallList, blueBallList, "welfare_lottery_2");

    }

    private void createLotteryInfo(List<List<Integer>> redBallList, List<List<Integer>> blueBallList,
                                   String tableName) {
        List<WelfareLottery> welfareLotteryList = new ArrayList<>();
        for (int r_i = 0, r_len = redBallList.size(); r_i < r_len; r_i++) {
            List<Integer> redBall = redBallList.get(r_i);
            for (List<Integer> blueBall : blueBallList) {
                welfareLotteryList.add(batchSaveCreateLotteryInfo(redBall, blueBall));
            }
            // 第一次存储的时候需要创建表
            if (r_i % 50000 == 0) {
                tableName = LotteryUtils.getTableNameCount(tableName, 3);
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
    }

    private WelfareLottery batchSaveCreateLotteryInfo(List<Integer> redBall, List<Integer> blueBall) {
        WelfareLottery welfareLottery = new WelfareLottery();
        welfareLottery.setUuid(UUID.randomUUID().toString());
        welfareLottery.setRed1(LotteryUtils.number2String(redBall.get(0)));
        welfareLottery.setRed2(LotteryUtils.number2String(redBall.get(1)));
        welfareLottery.setRed3(LotteryUtils.number2String(redBall.get(2)));
        welfareLottery.setRed4(LotteryUtils.number2String(redBall.get(3)));
        welfareLottery.setRed5(LotteryUtils.number2String(redBall.get(4)));
        welfareLottery.setRed6(LotteryUtils.number2String(redBall.get(5)));
        welfareLottery.setBlue(LotteryUtils.number2String(blueBall.get(0)));
        welfareLottery.setLotteryInfo(LotteryUtils.getCreateLotteryInfo(redBall, blueBall));
        return welfareLottery;
    }

    private void createLotteryInfo1(List<List<Integer>> redBallList, List<List<Integer>> blueBallList, String tableName) {
        List<String> lotteryInfoList = LotteryUtils.randomLotteryInfo(redBallList, blueBallList);

        List<WelfareLottery> welfareLotteryList = new ArrayList<>();
        for (int l_i = 0, l_len = lotteryInfoList.size(); l_i < l_len; l_i++) {
            int index = l_i + 1;
            String lotteryInfo = lotteryInfoList.get(l_i);
            welfareLotteryList.add(createWelfareLotteryInfo(lotteryInfo));

            // 第一次存储的时候需要创建表
            if (l_i % 800000 == 0) {
                tableName = LotteryUtils.getTableNameCount(tableName, 3);
                baseMapper.createWelfareSubTable(tableName);
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
                int insertCount = baseMapper.customBatchInsert(tableName, welfareLotteryList);
                if (insertCount != 0) {
                    welfareLotteryList.clear();
                }
            }
        }

    }

    private WelfareLottery createWelfareLotteryInfo(String lotteryInfo) {
        WelfareLottery welfareLottery = new WelfareLottery();
        welfareLottery.setUuid(UUID.randomUUID().toString());
        String[] lotteryInfoArray = lotteryInfo.split("---");
        String redBall = lotteryInfoArray[0];
        String blueBall = lotteryInfoArray[1];
        String[] redBallArray = redBall.split("-");
        welfareLottery.setRed1(LotteryUtils.number2String(redBallArray[0]));
        welfareLottery.setRed2(LotteryUtils.number2String(redBallArray[1]));
        welfareLottery.setRed3(LotteryUtils.number2String(redBallArray[2]));
        welfareLottery.setRed4(LotteryUtils.number2String(redBallArray[3]));
        welfareLottery.setRed5(LotteryUtils.number2String(redBallArray[4]));
        welfareLottery.setRed6(LotteryUtils.number2String(redBallArray[5]));
        welfareLottery.setBlue(LotteryUtils.number2String(blueBall));
        welfareLottery.setLotteryInfo(lotteryInfo);
        return welfareLottery;
    }

    private void addDataToDatabase(JSONObject resultData, String lastIssue) {
        JSONArray data = resultData.getJSONArray("result");
        for (int d_i = 0, d_len = data.size(); d_i < d_len; d_i++) {
            JSONObject oneData = data.getJSONObject(d_i);

            String issue = oneData.getString("code");
            if (StringUtils.isNotEmpty(lastIssue) && lastIssue.equals(issue)) {
                break;
            }

            WelfareLottery welfareLottery = new WelfareLottery();

            welfareLottery.setUuid(UUID.randomUUID().toString());
            welfareLottery.setIssue(issue);
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
            baseMapper.insert(welfareLottery);
        }
    }
}
