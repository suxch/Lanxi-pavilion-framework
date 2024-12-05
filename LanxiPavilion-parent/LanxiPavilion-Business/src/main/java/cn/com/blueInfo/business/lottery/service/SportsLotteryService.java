package cn.com.blueInfo.business.lottery.service;

import cn.com.blueInfo.business.lottery.entity.SportsLottery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SportsLotteryService extends IService<SportsLottery> {

    List<String> querySportsLotteryInfo(String birthday);

    void addSportsLotteryDataForHttp();

    void updateLatestLotteryDataForHttp();

    void createLotteryInfo();

}
