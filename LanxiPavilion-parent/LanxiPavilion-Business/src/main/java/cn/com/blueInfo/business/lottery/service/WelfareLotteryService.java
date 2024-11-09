package cn.com.blueInfo.business.lottery.service;

import cn.com.blueInfo.business.lottery.entity.WelfareLottery;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WelfareLotteryService extends IService<WelfareLottery> {

    void addWelfareLotteryDataForHttp();

    void createWelfareLotteryData();

    void updateLatestData();

}
