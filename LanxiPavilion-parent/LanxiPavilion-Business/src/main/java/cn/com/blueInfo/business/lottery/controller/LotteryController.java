package cn.com.blueInfo.business.lottery.controller;

import cn.com.blueInfo.business.lottery.service.SportsLotteryService;
import cn.com.blueInfo.business.lottery.service.WelfareLotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LotteryController {

    @Autowired
    private SportsLotteryService sportsLotteryService;

    @Autowired
    private WelfareLotteryService welfareLotteryService;

}
