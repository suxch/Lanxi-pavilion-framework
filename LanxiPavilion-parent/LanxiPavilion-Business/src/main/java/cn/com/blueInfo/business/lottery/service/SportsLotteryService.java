package cn.com.blueInfo.business.lottery.service;

import cn.com.blueInfo.business.lottery.entity.SportsLottery;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SportsLotteryService extends IService<SportsLottery> {

    void addSportsLotteryDataForHttp();

    void updateLatestLotteryDataForHttp();

    void createLotteryInfo();

}
