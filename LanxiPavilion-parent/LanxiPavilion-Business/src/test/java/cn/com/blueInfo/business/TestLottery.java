package cn.com.blueInfo.business;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.Random;

@Log4j2
public class TestLottery {

    @Test
    public void lottery() {
        int count = 0;
        int count01 = 0, count02 = 0, count03 = 0, count04 = 0, count05 = 0;
        int count06 = 0, count07 = 0, count08 = 0, count09 = 0, count10 = 0;
        int count11 = 0, count12 = 0, count13 = 0, count14 = 0, count15 = 0;
        int count16 = 0, count17 = 0, count18 = 0, count19 = 0, count20 = 0;
        int count21 = 0, count22 = 0, count23 = 0, count24 = 0, count25 = 0;
        int count26 = 0, count27 = 0, count28 = 0, count29 = 0, count30 = 0;
        int count31 = 0, count32 = 0, count33 = 0;
        Random random = new Random(System.currentTimeMillis());
        while (count < 330) {
            int currNum = random.nextInt(34);
            switch (currNum) {
                case 1:
//                    log.info("数字1=" + count);
                    count01++;
                    break;
                case 2:
//                    log.info("数字2=" + count);
                    count02++;
                    break;
                case 3:
//                    log.info("数字3=" + count);
                    count03++;
                    break;
                case 4:
//                    log.info("数字4=" + count);
                    count04++;
                    break;
                case 5:
//                    log.info("数字5=" + count);
                    count05++;
                    break;
                case 6:
//                    log.info("数字6=" + count);
                    count06++;
                    break;
                case 7:
//                    log.info("数字7=" + count);
                    count07++;
                    break;
                case 8:
//                    log.info("数字8=" + count);
                    count08++;
                    break;
                case 9:
//                    log.info("数字9=" + count);
                    count09++;
                    break;
                case 10:
//                    log.info("数字10=" + count);
                    count10++;
                    break;
                case 11:
//                    log.info("数字11=" + count);
                    count11++;
                    break;
                case 12:
//                    log.info("数字12=" + count);
                    count12++;
                    break;
                case 13:
//                    log.info("数字13=" + count);
                    count13++;
                    break;
                case 14:
//                    log.info("数字14=" + count);
                    count14++;
                    break;
                case 15:
//                    log.info("数字15=" + count);
                    count15++;
                    break;
                case 16:
//                    log.info("数字16=" + count);
                    count16++;
                    break;
                case 17:
//                    log.info("数字17=" + count);
                    count17++;
                    break;
                case 18:
//                    log.info("数字18=" + count);
                    count18++;
                    break;
                case 19:
//                    log.info("数字19=" + count);
                    count19++;
                    break;
                case 20:
//                    log.info("数字20=" + count);
                    count20++;
                    break;
                case 21:
//                    log.info("数字21=" + count);
                    count21++;
                    break;
                case 22:
//                    log.info("数字22=" + count);
                    count22++;
                    break;
                case 23:
//                    log.info("数字23=" + count);
                    count23++;
                    break;
                case 24:
//                    log.info("数字24=" + count);
                    count24++;
                    break;
                case 25:
//                    log.info("数字25=" + count);
                    count25++;
                    break;
                case 26:
//                    log.info("数字26=" + count);
                    count26++;
                    break;
                case 27:
//                    log.info("数字27=" + count);
                    count27++;
                    break;
                case 28:
//                    log.info("数字28=" + count);
                    count28++;
                    break;
                case 29:
//                    log.info("数字29=" + count);
                    count29++;
                    break;
                case 30:
//                    log.info("数字30=" + count);
                    count30++;
                    break;
                case 31:
//                    log.info("数字31=" + count);
                    count31++;
                    break;
                case 32:
//                    log.info("数字32=" + count);
                    count32++;
                    break;
                case 33:
//                    log.info("数字33=" + count);
                    count33++;
                    break;
                default:
                    break;
            }
            count++;
        }
        log.info("数字01出现的次数：" + count01);
        log.info("数字02出现的次数：" + count02);
        log.info("数字03出现的次数：" + count03);
        log.info("数字04出现的次数：" + count04);
        log.info("数字05出现的次数：" + count05);
        log.info("数字06出现的次数：" + count06);
        log.info("数字07出现的次数：" + count07);
        log.info("数字08出现的次数：" + count08);
        log.info("数字09出现的次数：" + count09);
        log.info("数字10出现的次数：" + count10);
        log.info("数字11出现的次数：" + count11);
        log.info("数字12出现的次数：" + count12);
        log.info("数字13出现的次数：" + count13);
        log.info("数字14出现的次数：" + count14);
        log.info("数字15出现的次数：" + count15);
        log.info("数字16出现的次数：" + count16);
        log.info("数字17出现的次数：" + count17);
        log.info("数字18出现的次数：" + count18);
        log.info("数字19出现的次数：" + count19);
        log.info("数字20出现的次数：" + count20);
        log.info("数字21出现的次数：" + count21);
        log.info("数字22出现的次数：" + count22);
        log.info("数字23出现的次数：" + count23);
        log.info("数字24出现的次数：" + count24);
        log.info("数字25出现的次数：" + count25);
        log.info("数字26出现的次数：" + count26);
        log.info("数字27出现的次数：" + count27);
        log.info("数字28出现的次数：" + count28);
        log.info("数字29出现的次数：" + count29);
        log.info("数字30出现的次数：" + count30);
        log.info("数字31出现的次数：" + count31);
        log.info("数字32出现的次数：" + count32);
        log.info("数字33出现的次数：" + count33);
    }

    @Test
    public void createSql() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE `sports_lottery`  (").append("\r\n");
        stringBuilder.append("  `uuid` varchar(64) NOT NULL,").append("\r\n");
        stringBuilder.append("  `issue` varchar(16),").append("\r\n");
        stringBuilder.append("  `date` varchar(32),").append("\r\n");
        for (int r_i = 0, r_len = 35; r_i < r_len; r_i++) {
            String redNum = "";
            if (r_i < 9) {
                redNum = "0" + (r_i + 1);
            } else {
                redNum = "" + (r_i + 1);
            }
            stringBuilder.append("  `red").append(redNum).append("` varchar(2),").append("\r\n");
        }
        for (int b_i = 0, b_len = 12; b_i < b_len; b_i++) {
            String blueNum = "";
            if (b_i < 9) {
                blueNum = "0" + (b_i + 1);
            } else {
                blueNum = "" + (b_i + 1);
            }
            stringBuilder.append("  `blue").append(blueNum).append("` varchar(2),").append("\r\n");
        }
        stringBuilder.append("  `lottery_info` varchar(255),").append("\r\n");
        stringBuilder.append("  `first_base_num` varchar(4),").append("\r\n");
        stringBuilder.append("  `first_base_money` varchar(255),").append("\r\n");
        stringBuilder.append("  `first_append_num` varchar(4),").append("\r\n");
        stringBuilder.append("  `first_append_money` varchar(255),").append("\r\n");
        stringBuilder.append("  `second_base_num` varchar(5),").append("\r\n");
        stringBuilder.append("  `second_base_money` varchar(255),").append("\r\n");
        stringBuilder.append("  `second_append_num` varchar(4),").append("\r\n");
        stringBuilder.append("  `second_append_money` varchar(255),").append("\r\n");
        stringBuilder.append("  PRIMARY KEY (`uuid`) USING BTREE").append("\r\n");
        stringBuilder.append(")");
        System.out.println(stringBuilder);
    }

}
