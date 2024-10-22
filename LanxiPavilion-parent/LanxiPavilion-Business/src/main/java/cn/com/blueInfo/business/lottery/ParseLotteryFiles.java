package cn.com.blueInfo.business.lottery;

import cn.com.blueInfo.business.lottery.enums.SportsLotteryEnum;
import cn.com.blueInfo.business.lottery.enums.WelfareLotteryEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class ParseLotteryFiles {

    public static void main(String[] args) {
        String folderName = "D:" + File.separator +
                "中国彩票基础数据" + File.separator +
                "大乐透";
        File folder = new File(folderName);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getAbsolutePath();
                    if (fileName.contains("双色球")) {
                        parseWelfareLottery(fileName);
                    } else if (fileName.contains("大乐透")) {
                        parseSportsLottery(fileName);
                    }
                }
            }
        }
    }

    /**
     * 解析体育彩票——大乐透
     * @param fileName
     */
    public static void parseSportsLottery(String fileName) {
        File file = new File(fileName);
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements table = doc.select(".m-historyTab");
            // 解析表头
            parseSportsLotteryHead(table);
            System.out.println();
            // 解析表体
            parseSportsLotteryBody(table);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseSportsLotteryHead(Elements table) {
        Elements thead_tr = table.select("thead").select("tr");
        Elements thead_tr_th = thead_tr.first().select("th");
        for (Element oneNode : thead_tr_th) {
            System.out.println(oneNode.text());
        }
    }

    public static void parseSportsLotteryBody(Elements table) {
        Elements tbody_tr = table.select("#historyData").select("tr");
        for (Element oneRow : tbody_tr) {
            Elements tbody_tr_td = oneRow.select("td");

            for (int t_i = 0, t_len = tbody_tr_td.size(); t_i < t_len; t_i++) {
                Element oneColumn = tbody_tr_td.get(t_i);
                //System.out.println(oneColumn);
                if (t_i < 2 || t_i > 8) {
                    String title = SportsLotteryEnum.getChineseByCode(t_i);
                    if (!"N/A".equals(title)) {
                        System.out.println(title + "：" + oneColumn.text());
                    }
                }
                if (t_i >= 2 && t_i < 9) {
                    StringBuilder sportsLottery = new StringBuilder();
                    Elements reds = oneRow.select(".u-dltpre");
                    for (Element redBall : reds) {
                        sportsLottery.append(redBall.text()).append("-");
                    }
                    sportsLottery.append("--");
                    Elements blues = oneRow.select(".u-dltnext");
                    for (Element blueBall : blues) {
                        sportsLottery.append(blueBall.text()).append("-");
                    }
                    sportsLottery.setLength(sportsLottery.length() - 1);
                    System.out.println(SportsLotteryEnum.getChineseByCode(t_i) + "：" + sportsLottery);
                    t_i = 8;
                }
            }
            System.out.println();
        }
    }

    /**
     * 解析福利彩票——双色球
     * @param fileName
     */
    public static void parseWelfareLottery(String fileName) {
        File file = new File(fileName);
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements table = doc.select(".ssq_table");
            // 解析表头
            parseWelfareLotteryHead(table);
            System.out.println();
            // 解析表体
            parseWelfareLotteryBody(table);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析福利彩票表头
     * @param table
     */
    public static void parseWelfareLotteryHead(Elements table) {
        Elements thead_tr = table.select("thead").select("tr");
        Elements thead_tr_th = thead_tr.first().select("th");
        for (Element oneNode : thead_tr_th) {
            System.out.println(oneNode.text());
        }
    }

    /**
     * 解析福利彩票表体
     * @param table
     */
    public static void parseWelfareLotteryBody(Elements table) {
        Elements tbody_tr = table.select("tbody").select("tr");
        for (Element oneRow : tbody_tr) {
            Elements tbody_tr_td = oneRow.select("td");
            for (int t_i = 0, t_len = tbody_tr_td.size(); t_i < t_len; t_i++) {
                Element oneColumn = tbody_tr_td.get(t_i);
                if (t_i == 2) {
                    Element column_div = oneColumn.select(".qiu").first();
                    Elements div_red = column_div.getElementsByClass("qiu-item-wqgg-zjhm-red");
                    StringBuilder welfareLottery = new StringBuilder();
                    for (Element redBall : div_red) {
                        welfareLottery.append(redBall.text()).append("-");
                    }
                    welfareLottery.append("--");
                    Element div_blue = column_div.getElementsByClass("qiu-item-wqgg-zjhm-blue").first();
                    welfareLottery.append(div_blue.text());
                    System.out.println(WelfareLotteryEnum.getChineseByCode(t_i) + "：" + welfareLottery);
                } else {
                    String title = WelfareLotteryEnum.getChineseByCode(t_i);
                    if (!"N/A".equals(title)) {
                        System.out.println(title + "：" + oneColumn.text());
                    }
                }
            }
            System.out.println();
        }
    }

}
