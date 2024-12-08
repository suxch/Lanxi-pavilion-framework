package cn.com.blueInfo.business.lottery.service;

import cn.com.blueInfo.business.lottery.entity.SportsLottery;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SportsLotteryService extends IService<SportsLottery> {

    JSONArray queryLotteryInfoByString(String lotteryInfo);

    List<String> querySportsLotteryInfo(String birthday);

    void addSportsLotteryDataForHttp();

    void updateLatestLotteryDataForHttp();

    void createLotteryInfo();

}
