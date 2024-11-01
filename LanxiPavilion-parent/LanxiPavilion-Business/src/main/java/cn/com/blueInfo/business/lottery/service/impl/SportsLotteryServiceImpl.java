package cn.com.blueInfo.business.lottery.service.impl;

import cn.com.blueInfo.business.lottery.mapper.SportsLotteryMapper;
import cn.com.blueInfo.business.lottery.service.SportsLotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SportsLotteryServiceImpl implements SportsLotteryService {

    @Autowired
    private SportsLotteryMapper sportsLotteryMapper;

}
