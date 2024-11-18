package cn.com.blueInfo.business.lottery.util;

import java.util.ArrayList;
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

}
