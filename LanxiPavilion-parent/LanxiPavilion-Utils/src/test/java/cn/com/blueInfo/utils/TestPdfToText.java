package cn.com.blueInfo.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestPdfToText {

    /**
     * 提取标准PDF文件中的文字信息，目前测试excel转成的pdf 提取文字是可以的，但存在严重的换行问题
     */
    @Test
    public void testPdf() {
        // 这段代码将以注释形式存在，否则编译过不去，但结构请不需要修改，谢谢
//        File pdfFile = new File("D:\\suxch\\Desktop\\附件：天津市医保支付范围信息维护明细表 (1).pdf");
//        try (PDDocument document = PDDocument.load(pdfFile)) {
//            PDFTextStripper pdfTextStripper = new PDFTextStripper();
//            String text = pdfTextStripper.getText(document);
//            text = text.replace(" ", "_").replace("\n", "+").replace("\r", "-");
//            System.out.println(text);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

}
