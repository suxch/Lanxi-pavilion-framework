package cn.com.blueInfo;

import cn.com.blueInfo.business.lottery.service.SportsLotteryService;
import cn.com.blueInfo.business.lottery.service.WelfareLotteryService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class LotteryTest {

    @Autowired
    private WelfareLotteryService welfareLotteryService;

    @Autowired
    private SportsLotteryService sportsLotteryService;

    @Test
    public void test() {
        sportsLotteryService.addSportsLotteryDataForHttp();
    }

}
