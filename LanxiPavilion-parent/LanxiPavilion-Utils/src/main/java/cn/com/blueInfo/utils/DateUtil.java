package cn.com.blueInfo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 日期，时间工具类
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2018/8/13 20:59
 * @Version: 1.0
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat_CN = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    private static final SimpleDateFormat dateFormatForDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateUtil dateUtil = new DateUtil();

    /**
     * 获取格式化后的时间（yyyy-MM-dd HH:mm:ss）
     * @Title: getFormatDate
     * @param date 格林威治时间或时间戳
     * @return String
     */
    public static String getFormatDateTime(Date date) {
        return dateFormat.format(date);
    }

    /**
     * 获取格式化后的时间（yyyy年MM月dd日 HH时mm分ss秒）
     * 该方法仅限展示使用，不做反向格式化时使用
     * @Title: getFormatDate_CN
     * @param date 格林威治时间或时间戳
     * @return String
     */
    public static String getFormatDateTime_CN(Date date) {
        return dateFormat_CN.format(date);
    }

    /**
     * 获取格式化后的时间（yyyy-MM-dd）
     * @Title: getFormatDate
     * @param date 格林威治时间或时间戳
     * @return String
     */
    public static String getFormatDate(Date date) {
        return dateFormatForDate.format(date);
    }

    /**
     * 获取验证码失效时间
     * @Title: getValiCodeFailureTime
     * @param sendDateStr
     * @return String
     */
    public static String getValiCodeFailureTime(String sendDateStr) {
        Date sendDate = dateUtil.getGreenwichTime(sendDateStr);
        Calendar calendar = dateUtil.setTimeForDate(sendDate);
        calendar = dateUtil.addOrCutDownForMinute(calendar, 30);
        return getFormatDateTime(calendar.getTime());
    }

    /**
     * 获取验证码失效时间
     * @Title: getValiCodeFailureTime
     * @param sendDateStr
     * @return String
     */
    public static String getSendEndDate(String sendDateStr) {
        Date sendDate = dateUtil.getGreenwichTimeByDateStr(sendDateStr);
        Calendar calendar = dateUtil.setTimeForDate(sendDate);
        calendar = dateUtil.addOrCutDownForDay(calendar, 1);
        return getFormatDate(calendar.getTime());
    }

    /**
     * 设置月份（日记账系统journal）
     * @Title: setMonthForJournal
     * @param oldRecord
     * @return String
     * @throws
     */
    public static String setMonthForJournal(String oldRecord) {
        String[] yearAndMonth = oldRecord.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(yearAndMonth[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(yearAndMonth[1]) - 1);
        calendar.add(Calendar.MONTH, 1);
        return getFormatDate(calendar.getTime());
    }

    /**
     * 增加或减少天数（返回日期）
     * @Title: addOrCutDownForDay
     * @param dayVal
     * @return String
     * @throws
     */
    public static String addOrCutDownForDay(Integer dayVal) {
        DateUtil dateUtil = new DateUtil();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar = dateUtil.addOrCutDownForDay(calendar, dayVal);
        return DateUtil.getFormatDate(calendar.getTime());
    }

    /**
     * 根据时间间隔获取对应的时间
     * @Title: getDateByDateInterval
     * @param minuteType
     * @return Date 格林威治时间
     */
    public static Date getDateByDateInterval(long minuteType) {
        int[] fixedMinute = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        if (minuteType == 60 * 1000) { // 每分钟执行一次
            calendar.add(Calendar.MINUTE, 1);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        } else if (minuteType == 5 * 60 * 1000) { // 每隔五分钟执行一次
            fixedMinute = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
        } else if (minuteType == 10 * 60 * 1000) { // 每隔十分钟执行一次
            fixedMinute = new int[]{10, 20, 30, 40, 50};
        } else if (minuteType == 15 * 60 * 1000) { // 每隔十五分钟执行一次
            fixedMinute = new int[]{15, 30, 45};
        } else if (minuteType == 60 * 60 * 1000) { // 每隔一小时执行一次
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        } else if (minuteType == 24 * 60 * 60 * 1000) { // 每隔一天执行一次
            calendar.set(Calendar.HOUR_OF_DAY, 14);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if (calendar.getTime().before(new Date())) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                return calendar.getTime();
            } else {
                return calendar.getTime();
            }
        }
        int currentMinute = calendar.get(Calendar.MINUTE);
        if (fixedMinute != null) {
            if (fixedMinute[fixedMinute.length - 1] < currentMinute) {
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                calendar.set(Calendar.MINUTE, 0);
            } else {
                for (int f_i = 0, f_len = fixedMinute.length; f_i < f_len; f_i++) {
                    if (fixedMinute[f_i] > currentMinute) {
                        calendar.set(Calendar.MINUTE, fixedMinute[f_i]);
                        break;
                    }
                }
            }
        }
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据时间间隔获取对应的时间
     * @Title: getDateByDateInterval
     * @param minuteType
     * @param startTime
     * @return Date 格林威治时间
     */
    public static Date getDateByDateInterval(long minuteType, String startTime) {
        int[] fixedMinute = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        if (minuteType == 60 * 1000) { // 每分钟执行一次
            calendar.add(Calendar.MINUTE, 1);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        } else if (minuteType == 5 * 60 * 1000) { // 每隔五分钟执行一次
            fixedMinute = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
        } else if (minuteType == 10 * 60 * 1000) { // 每隔十分钟执行一次
            fixedMinute = new int[]{10, 20, 30, 40, 50};
        } else if (minuteType == 15 * 60 * 1000) { // 每隔十五分钟执行一次
            fixedMinute = new int[]{15, 30, 45};
        } else if (minuteType == 60 * 60 * 1000) { // 每隔一小时执行一次
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        } else if (minuteType == 24 * 60 * 60 * 1000) { // 每隔一天执行一次
            String[] times = startTime.split(":");
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(times[1]));
            calendar.set(Calendar.SECOND, Integer.parseInt(times[2]));
            if (calendar.getTime().before(new Date())) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                return calendar.getTime();
            } else {
                return calendar.getTime();
            }
        }
        int currentMinute = calendar.get(Calendar.MINUTE);
        if (fixedMinute != null) {
            if (fixedMinute[fixedMinute.length - 1] < currentMinute) {
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                calendar.set(Calendar.MINUTE, 0);
            } else {
                for (int f_i = 0, f_len = fixedMinute.length; f_i < f_len; f_i++) {
                    if (fixedMinute[f_i] > currentMinute) {
                        calendar.set(Calendar.MINUTE, fixedMinute[f_i]);
                        break;
                    }
                }
            }
        }
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定格式日期的时间戳 (yyyy-MM-dd HH:mm:ss)
     * @Title: getTimeStamp
     * @param dateStr
     * @return Long
     */
    public static Long getTimeStamp(String dateStr) {
        Long timeStamp = null;
        try {
            Date date = dateFormat.parse(dateStr);
            timeStamp = date.getTime();
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return timeStamp;
    }

    /**
     * 获取指定格式的格林威治时间(yyyy-MM-dd HH:mm:ss)
     * @Title: getGreenwichTime
     * @param dateStr
     * @return Date
     * @throws
     */
    protected Date getGreenwichTime(String dateStr) {
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return date;
    }

    /**
     * @description: 获取指定格式的格林威治时间(yyyy-MM-dd)
     * @author: suxch
     * @date: 2024/8/13 21:11
     * @param: [dateStr]
     * @return: java.util.Date
     **/
    protected Date getGreenwichTimeByDateStr(String dateStr) {
        Date date = null;
        try {
            date = dateFormatForDate.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return date;
    }

    /**
     * 增加或减少年份
     * @Title: addOrCutDownForYear
     * @param calendar
     * @param yearVal
     * @return Calendar
     * @throws
     */
    protected Calendar addOrCutDownForYear(Calendar calendar, Integer yearVal) {
        calendar.add(Calendar.YEAR, yearVal);
        return calendar;
    }

    /**
     * 增加或减少月份
     * @Title: addOrCutDownForMonth
     * @param calendar
     * @param monthVal
     * @return
     * @return Calendar
     * @throws
     */
    protected Calendar addOrCutDownForMonth(Calendar calendar, Integer monthVal) {
        calendar.add(Calendar.MONTH, monthVal);
        return calendar;
    }

    /**
     * 增加或减少天数
     * @Title: addOrCutDownForDay
     * @param calendar
     * @param dayVal
     * @return Calendar
     * @throws
     */
    protected Calendar addOrCutDownForDay(Calendar calendar, Integer dayVal) {
        calendar.add(Calendar.DAY_OF_YEAR, dayVal);
        return calendar;
    }

    /**
     * 增加或减少小时数
     * @Title: addOrCutDownForHour
     * @param calendar
     * @param hourVal
     * @return Calendar
     * @throws
     */
    protected Calendar addOrCutDownForHour(Calendar calendar, Integer hourVal) {
        calendar.add(Calendar.HOUR_OF_DAY, hourVal);
        return calendar;
    }

    /**
     * 增加或减少分钟
     * @Title: addOrCutDownForMinute
     * @param calendar
     * @param minuteVal
     * @return Calendar
     * @throws
     */
    protected Calendar addOrCutDownForMinute(Calendar calendar, Integer minuteVal) {
        calendar.add(Calendar.MINUTE, minuteVal);
        return calendar;
    }

    /**
     * 增加或减少秒
     * @Title: addOrCutDownForSecond
     * @param calendar
     * @param secondVal
     * @return Calendar
     * @throws
     */
    protected Calendar addOrCutDownForSecond(Calendar calendar, Integer secondVal) {
        calendar.add(Calendar.SECOND, secondVal);
        return calendar;
    }

    /**
     * 将格林威治时间放入日历对象中
     * @Title: setTimeForDate
     * @param date
     * @return Calendar
     * @throws
     */
    public Calendar setTimeForDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            date = new Date();
        }
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 将时间戳放入日历对象中
     * @Title: setTimeForTimeStamp
     * @param timeStamp
     * @return Calendar
     * @throws
     */
    protected Calendar setTimeForTimeStamp(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        if (timeStamp == null) {
            timeStamp = System.currentTimeMillis();
        }
        calendar.setTimeInMillis(timeStamp);
        return calendar;
    }
}
