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

    @Test
    public void testW() {
        welfareLotteryService.addWelfareLotteryDataForHttp();
    }

    @Test
    public void testQuery() {
        welfareLotteryService.updateLatestLotteryDataForHttp();
    }

    @Test
    public void createWelfareLotteryInfo() {
        welfareLotteryService.createLotteryInfo();
    }

    @Test
    public void createSportsLotteryInfo() {
        sportsLotteryService.createLotteryInfo();
    }

    @Test
    public void updateSportsLotteryInfo() {
        sportsLotteryService.updateLatestLotteryDataForHttp();
    }

    @Test
    public void queryWelfareLotteryInfo() {
        welfareLotteryService.queryWelfareLotteryInfo("");
    }

    @Test
    public void querySportsLotteryInfo() {
        String b = "19941017";
        sportsLotteryService.querySportsLotteryInfo(b);
        System.out.println("--------------------------------------------");
        welfareLotteryService.queryWelfareLotteryInfo(b);
    }

    @Test
    public void queryWelfareLotteryInfoByString() {
        welfareLotteryService.queryLotteryInfoByString("04-07-08-17-22-26---15");
    }

    @Test
    public void querySportsLotteryInfoByString() {
        sportsLotteryService.queryLotteryInfoByString("04-07-08-17-22---08-11");
    }

}
