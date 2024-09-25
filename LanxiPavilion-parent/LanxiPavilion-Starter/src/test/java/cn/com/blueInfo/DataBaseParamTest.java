package cn.com.blueInfo;

import cn.com.blueInfo.utils.entity.DataBaseParam;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class DataBaseParamTest {

    @Autowired
    private DataBaseParam dataBaseParam;

    @Test
    public void test() {
        log.info(dataBaseParam.getUrl());
        log.info(dataBaseParam.getUsername());
        log.info(dataBaseParam.getPassword());
        log.info(dataBaseParam.getDriverClassName());

        log.info(dataBaseParam.getDumpPath());
        log.info(dataBaseParam.getHost());
        log.info(dataBaseParam.getPort());
        log.info(dataBaseParam.getDatabase());
    }

}
