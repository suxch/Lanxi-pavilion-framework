package cn.com.blueInfo.business.lottery;

import cn.com.blueInfo.business.lottery.entity.SportsLottery;
import cn.com.blueInfo.business.lottery.entity.SportsLottery_All;
import cn.com.blueInfo.business.lottery.entity.WelfareLottery;
import cn.com.blueInfo.business.lottery.entity.WelfareLottery_All;
import cn.com.blueInfo.business.lottery.enums.SportsLotteryEnum;
import cn.com.blueInfo.business.lottery.enums.WelfareLotteryEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ParseLotteryFiles {

    public static void main(String[] args) {
        String folderName = "D:" + File.separator +
                "中国彩票基础数据" + File.separator;

        Scanner scanner = new Scanner(System.in);
        System.out.println("1: 双色球");
        System.out.println("2: 大乐透");
        System.out.print("请选择：");
        int num = scanner.nextInt();

        if (num == 1) {
            folderName += "双色球";
        } else if (num == 2) {
            folderName += "大乐透";
        } else {
            System.out.println("选择错误，请重新启动程序");
            return;
        }

        startParse(folderName);
    }

    public static JSONArray startParse(String folderName) {
        File folder = new File(folderName);
        JSONArray allFileData = new JSONArray();
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getAbsolutePath();
                    if (fileName.contains("双色球")) {
                        JSONArray oneFileData = parseWelfareLottery(fileName);
                        allFileData.addAll(oneFileData);
                    } else if (fileName.contains("大乐透")) {
                        JSONArray oneFileData = parseSportsLottery(fileName);
                        allFileData.addAll(oneFileData);
                    }
                }
            }
        }
        return allFileData;
    }

    /**
     * 解析体育彩票——大乐透
     * @param fileName
     */
    public static JSONArray parseSportsLottery(String fileName) {
        File file = new File(fileName);
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements table = doc.select(".m-historyTab");
            // 解析表头
            //parseSportsLotteryHead(table);
            //System.out.println();
            // 解析表体
            return parseSportsLotteryBody(table);
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

    public static JSONArray parseSportsLotteryBody(Elements table) {
        Elements tbody_tr = table.select("#historyData").select("tr");
        JSONArray result = new JSONArray();
        for (Element oneRow : tbody_tr) {
            JSONObject oneData = new JSONObject();
            Elements tbody_tr_td = oneRow.select("td");

            for (int t_i = 0, t_len = tbody_tr_td.size(); t_i < t_len; t_i++) {
                Element oneColumn = tbody_tr_td.get(t_i);
                //System.out.println(oneColumn);
                if (t_i < 2 || t_i > 8) {
                    String title = SportsLotteryEnum.getChineseByCode(t_i);
                    if (!"N/A".equals(title)) {
                        //System.out.println(title + "：" + oneColumn.text());
                        oneData.put(SportsLotteryEnum.getSignByCode(t_i), oneColumn.text());
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
                    //System.out.println(SportsLotteryEnum.getChineseByCode(t_i) + "：" + sportsLottery);
                    oneData.put(SportsLotteryEnum.getSignByCode(t_i), sportsLottery);
                    t_i = 8;
                }
            }
            //System.out.println();
            result.add(oneData);
        }
        return makeSportsLotteryForm(result);
    }

    /**
     * 组装体彩数据表
     * @param data
     */
    public static JSONArray makeSportsLotteryForm(JSONArray data) {
        for (int d_i = 0, d_len = data.size(); d_i < d_len; d_i++) {
            JSONObject oneData = data.getJSONObject(d_i);
            String lotteryInfo = oneData.getString("lottery_info");
            String[] lotteries = lotteryInfo.split("-");
            for (int l_i = 0, l_len = lotteries.length; l_i < l_len; l_i++) {
                String oneNum = lotteries[l_i];
                if (l_i > 4) {
                    if (!oneNum.isEmpty()) {
                        oneData.put("blue" + (l_i - 6), oneNum);
                        if (oneNum.length() != 2) {
                            oneNum = "0" + oneNum;
                        }
                        oneData.put("blue" + oneNum, oneNum);
                    }
                } else {
                    oneData.put("red" + (l_i + 1), oneNum);
                    if (oneNum.length() != 2) {
                        oneNum = "0" + oneNum;
                    }
                    oneData.put("red" + oneNum, oneNum);
                }
            }
            oneData.put("uuid", UUID.randomUUID());
        }

        //System.out.println(data.toJSONString());
        List<SportsLottery> sportsLotteryList = JSONArray.parseArray(data.toJSONString(), SportsLottery.class);
        List<SportsLottery_All> sportsLotteryAllList = JSONArray.parseArray(data.toJSONString(), SportsLottery_All.class);
        return data;
    }

    /**
     * 解析福利彩票——双色球
     * @param fileName
     */
    public static JSONArray parseWelfareLottery(String fileName) {
        File file = new File(fileName);
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements table = doc.select(".ssq_table");
            // 解析表头
            //parseWelfareLotteryHead(table);
            //System.out.println();
            // 解析表体
            return parseWelfareLotteryBody(table);
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
    public static JSONArray parseWelfareLotteryBody(Elements table) {
        JSONArray result = new JSONArray();
        Elements tbody_tr = table.select("tbody").select("tr");
        for (Element oneRow : tbody_tr) {
            JSONObject oneData = new JSONObject();
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
                    //System.out.println(WelfareLotteryEnum.getChineseByCode(t_i) + "：" + welfareLottery);
                    oneData.put(WelfareLotteryEnum.getSignByCode(t_i), welfareLottery.toString());
                } else {
                    String title = WelfareLotteryEnum.getChineseByCode(t_i);
                    if (!"N/A".equals(title)) {
                        //System.out.println(title + "：" + oneColumn.text());
                        oneData.put(WelfareLotteryEnum.getSignByCode(t_i), oneColumn.text());
                    }
                }
            }
            //System.out.println();
            result.add(oneData);

        }
        return makeWelfareLotteryForm(result);
    }

    /**
     * 组装福彩数据表
     * @param data
     */
    public static JSONArray makeWelfareLotteryForm(JSONArray data) {
        for (int d_i = 0, d_len = data.size(); d_i < d_len; d_i++) {
            JSONObject oneData = data.getJSONObject(d_i);
            String lotteryInfo = oneData.getString("lottery_info");
            String[] lotteries = lotteryInfo.split("-");
            for (int l_i = 0, l_len = lotteries.length; l_i < l_len; l_i++) {
                String oneNum = lotteries[l_i];
                if (l_i > 5) {
                    if (!oneNum.isEmpty()) {
                        oneData.put("blue", oneNum);
                        if (oneNum.length() != 2) {
                            oneNum = "0" + oneNum;
                        }
                        oneData.put("blue" + oneNum, oneNum);
                    }
                } else {
                    oneData.put("red" + (l_i + 1), oneNum);
                    if (oneNum.length() != 2) {
                        oneNum = "0" + oneNum;
                    }
                    oneData.put("red" + oneNum, oneNum);
                }
            }
            oneData.put("uuid", UUID.randomUUID());
        }

        //System.out.println(data.toJSONString());
        List<WelfareLottery> welfareLotteryList = JSONArray.parseArray(data.toJSONString(), WelfareLottery.class);
        List<WelfareLottery_All> welfareLotteryAllList = JSONArray.parseArray(data.toJSONString(), WelfareLottery_All.class);
        return data;
    }

}
