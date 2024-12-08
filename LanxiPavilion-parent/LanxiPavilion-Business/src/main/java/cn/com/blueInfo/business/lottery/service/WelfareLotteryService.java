package cn.com.blueInfo.business.lottery.service;

import cn.com.blueInfo.business.lottery.entity.WelfareLottery;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface WelfareLotteryService extends IService<WelfareLottery> {

    JSONArray queryLotteryInfoByString(String lotteryInfo);

    List<String> queryWelfareLotteryInfo(String birthday);

    void addWelfareLotteryDataForHttp();

    void updateLatestLotteryDataForHttp();

    void createLotteryInfo();

}
