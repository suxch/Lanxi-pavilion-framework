package cn.com.blueInfo.business.lottery.enums;

/**
 * 大乐透彩票枚举类
 */
public enum SportsLotteryEnum {
    A(0, "期号"),
    B(1, "开奖日期"),
    C(2, "开奖信息"),
    D(9, "一等奖-开奖注数"),
    E(10, "一等奖-基本奖金"),
    F(11, "一等奖-追加注数"),
    G(12, "一等奖-追加奖金"),
    H(13, "二等奖-开奖注数"),
    I(14, "二等奖-基本奖金"),
    J(15, "二等奖-追加注数"),
    K(16, "二等奖-追加奖金");

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

}
