package cn.com.blueInfo.business.lottery.enums;

/**
 * 大乐透彩票枚举类
 */
public enum SportsLotteryEnum {
    issue(0, "期号"),
    date(1, "开奖日期"),
    lottery_info(2, "开奖信息"),
    first_base_num(9, "一等奖-开奖注数"),
    first_base_money(10, "一等奖-基本奖金"),
    first_append_num(11, "一等奖-追加注数"),
    first_append_money(12, "一等奖-追加奖金"),
    second_base_num(13, "二等奖-开奖注数"),
    second_base_money(14, "二等奖-基本奖金"),
    second_append_num(15, "二等奖-追加注数"),
    second_append_money(16, "二等奖-追加奖金");

    public final Integer code;

    public final String value;

    SportsLotteryEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getChineseByCode(Integer code) {
        for (SportsLotteryEnum sportsLotteryEnum : SportsLotteryEnum.values()) {
            if (code.equals(sportsLotteryEnum.code)) {
                return sportsLotteryEnum.value;
            }
        }
        return "N/A";
    }

    public static String getSignByCode(Integer code) {
        for (SportsLotteryEnum sportsLotteryEnum : SportsLotteryEnum.values()) {
            if (code.equals(sportsLotteryEnum.code)) {
                return sportsLotteryEnum.name();
            }
        }
        return "N/A";
    }

}
