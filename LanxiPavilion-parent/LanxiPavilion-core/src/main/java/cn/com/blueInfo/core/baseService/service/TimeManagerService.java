package cn.com.blueInfo.core.baseService.service;

import cn.com.blueInfo.core.baseService.timedTask.MySqlDump;
import cn.com.blueInfo.utils.DateUtil;
import cn.com.blueInfo.utils.entity.DataBaseParam;

import java.util.Date;
import java.util.Timer;

/**
 * 时间管理服务
 * @ClassName: TimeManagerService
 * @author suxch
 * @date 2018年3月27日  下午12:09:15
 */
public class TimeManagerService {
    /** 每天执行一次 */
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    /** 每小时执行一次 */
    private static final long PERIOD_HOUR = 60 * 60 * 1000;
    /** 每分钟执行一次 */
    private static final long PREIOD_MINUTE = 60 * 1000;
    /** 每五分钟执行一次 */
    private static final long PREIOD_FIVE_MINUTE = 5 * 60 * 1000;
    /** 每十分钟执行一次 */
    private static final long PREIOD_TEN_MINUTE = 10 * 60 * 1000;
    /** 每十五分钟执行一次 */
    private static final long PREIOD_FIFTEEN_MINUTE = 15 * 60 * 1000;

    public TimeManagerService(DataBaseParam dataBaseParam) {
        this.mysqlDump(dataBaseParam);
    }

    public void mysqlDump(DataBaseParam dataBaseParam) {
//        Timer timer = new Timer();
//        MySqlDump mysql = new MySqlDump(dataBaseParam);
//
//        Date firstDate = DateUtil.getDateByDateInterval(PREIOD_MINUTE);
//        timer.schedule(mysql, firstDate, PREIOD_MINUTE);
    }
}
