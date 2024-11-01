package cn.com.blueInfo.business.lottery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@TableName("welfare_lottery")
public class WelfareLottery {

    @TableId("uuid")
    private String uuid;
    private String issue;
    private String date;
    private String red1;
    private String red2;
    private String red3;
    private String red4;
    private String red5;
    private String red6;
    private String blue;
    private String lottery_info;
    private String first_num;
    private String first_money;
    private String second_num;
    private String second_money;

}
