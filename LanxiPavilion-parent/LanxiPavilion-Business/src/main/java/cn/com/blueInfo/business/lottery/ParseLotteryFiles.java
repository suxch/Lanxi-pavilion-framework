package cn.com.blueInfo.business.lottery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class ParseLotteryFiles {

    public static void main(String[] args) {
        String fileName = "D:" + File.separator +
                "中国彩票基础数据" + File.separator +
                "双色球-历史数据-01.txt";
        System.out.println(fileName);
        File file = new File(fileName);
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements html = doc.select(".ssq_table").select("thead").select("tr");
            System.out.println(html.get(0).select("th").get(0).text());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
