package cn.com.blueInfo.business.lottery.service.impl;

import cn.com.blueInfo.business.lottery.mapper.WelfareLotteryMapper;
import cn.com.blueInfo.business.lottery.service.WelfareLotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WelfareLotteryServiceImpl implements WelfareLotteryService {

    @Autowired
    private WelfareLotteryMapper welfareLotteryMapper;

    @Override
    public void addData() {

    }
}
