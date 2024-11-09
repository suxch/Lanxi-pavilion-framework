package cn.com.blueInfo.business;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.Random;

@Log4j2
public class TestLottery {

    @Test
    public void lottery() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0, len = 33; i < len; i++) {
            System.out.println(random.nextInt(17));
        }
    }

}
