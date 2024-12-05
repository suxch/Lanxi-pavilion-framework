package cn.com.blueInfo.business.lottery.service;

import cn.com.blueInfo.business.lottery.entity.WelfareLottery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface WelfareLotteryService extends IService<WelfareLottery> {

    List<String> queryWelfareLotteryInfo(String birthday);

    void addWelfareLotteryDataForHttp();

    void updateLatestLotteryDataForHttp();

    void createLotteryInfo();

}
