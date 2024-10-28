package cn.com.blueInfo.business.lottery.enums;

/**
 * 福利彩票枚举类
 */
public enum WelfareLotteryEnum {
    issue(0, "期号"),
    date(1, "开奖日期"),
    lottery_info(2, "开奖信息"),
    first_num(3, "一等奖-注数"),
    first_money(4, "一等奖-金额"),
    second_num(5, "二等奖-注数"),
    second_money(6, "二等奖-金额");

    public final Integer code;

    public final String value;

    WelfareLotteryEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getChineseByCode(Integer code) {
        for (WelfareLotteryEnum welfareLotteryEnum : WelfareLotteryEnum.values()) {
            if (code.equals(welfareLotteryEnum.code)) {
                return welfareLotteryEnum.value;
            }
        }
        return "N/A";
    }

    public static String getSignByCode(Integer code) {
        for (WelfareLotteryEnum welfareLotteryEnum : WelfareLotteryEnum.values()) {
            if (code.equals(welfareLotteryEnum.code)) {
                return welfareLotteryEnum.name();
            }
        }
        return "N/A";
    }
}
