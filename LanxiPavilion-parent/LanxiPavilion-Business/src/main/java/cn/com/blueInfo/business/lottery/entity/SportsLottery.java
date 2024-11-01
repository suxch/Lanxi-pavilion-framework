package cn.com.blueInfo.business.lottery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SportsLottery {

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
    private String lottery_info;
    private String first_base_num;
    private String first_base_money;
    private String first_append_num;
    private String first_append_money;
    private String second_base_num;
    private String second_base_money;
    private String second_append_num;
    private String second_append_money;

}
