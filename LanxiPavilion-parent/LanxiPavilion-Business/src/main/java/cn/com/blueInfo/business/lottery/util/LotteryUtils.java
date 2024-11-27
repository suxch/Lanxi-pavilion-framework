package cn.com.blueInfo.business.lottery.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LotteryUtils {

    /**
     * 生成组合球方法
     * @param total 总数
     * @param number 选择的个数
     * @return List<List<Integer>>
     */
    public static List<List<Integer>> generateCombinations(int total, int number) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        generateCombinationsInternal(total, number, 1, combination, result);
        return result;
    }

    private static void generateCombinationsInternal(int total, int number, int start, List<Integer> combination,
                                                     List<List<Integer>> result) {
        if (combination.size() == number) {
            result.add(new ArrayList<>(combination));  // 直接添加当前组合对象引用，后续统一处理复制等操作
            return;
        }
        for (int i = start; i <= total; i++) {
            combination.add(i);
            generateCombinationsInternal(total, number, i + 1, combination, result);
            combination.remove(combination.size() - 1);
        }
    }

    /**
     * 获取表名
     * @param tableName 表名
     * @param nameLength 表名长度
     * @return String
     */
    public static String getTableNameCount(String tableName, int nameLength) {
        String result = "";
        String[] names = tableName.split("_");
        if (names.length == nameLength) {
            result = tableName + "_01";
        } else {
            int tableCount = Integer.parseInt(names[nameLength]);
            tableCount++;
            result = tableName.substring(0, tableName.length() - 2)
                    + (String.valueOf(tableCount).length() == 1 ? "0" + tableCount : "" + tableCount);
        }
        return result;
    }

    /**
     * 获取创建彩票信息
     * @param redBall 红球
     * @param blueBall 蓝球
     * @return String
     */
    public static String getCreateLotteryInfo(List<Integer> redBall, List<Integer> blueBall) {
        StringBuilder lotteryInfo = new StringBuilder();
        for (Integer num : redBall) {
            lotteryInfo.append(number2String(num)).append("-");
        }
        lotteryInfo.append("--");
        for (Integer num : blueBall) {
            lotteryInfo.append(number2String(num)).append("-");
        }
        lotteryInfo.setLength(lotteryInfo.length() - 1);
        return lotteryInfo.toString();
    }

    /**
     * 数字补零转字符串
     * @param number 数字
     * @return String
     */
    public static String number2String(Integer number) {
        return String.valueOf(number).length() == 1 ? "0" + number : number + "";
    }

    /**
     * 数字补零转字符串
     * @param number 数字
     * @return String
     */
    public static String number2String(String number) {
        return number.length() == 1 ? "0" + number : number;
    }

    /**
     * 随机生成彩票信息
     * @param redBallList 红球合集
     * @param blueBallList 篮球合集
     * @return List<String>
     */
    public static List<String> randomLotteryInfo(List<List<Integer>> redBallList, List<List<Integer>> blueBallList) {
        List<String> lotteryInfoList = new ArrayList<>();
        for (List<Integer> redBall : redBallList) {
            for (List<Integer> blueBall : blueBallList) {
                lotteryInfoList.add(getCreateLotteryInfo(redBall, blueBall));
            }
        }

        Collections.shuffle(lotteryInfoList);

        return lotteryInfoList;
    }

}
