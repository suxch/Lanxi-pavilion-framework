package cn.com.blueInfo.business.lottery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SportsLottery {

    @TableId(value = "uuid", type = IdType.INPUT)
    private String uuid;
    private String issue;
    private String date;
    private String red1;
    private String red2;
    private String red3;
    private String red4;
    private String red5;
    private String blue1;
    private String blue2;
    private String lotteryInfo;
    private String firstBaseNum;
    private String firstBaseMoney;
    private String firstAppendNum;
    private String firstAppendMoney;
    private String secondBaseNum;
    private String secondBaseMoney;
    private String secondAppendNum;
    private String secondAppendMoney;

}
