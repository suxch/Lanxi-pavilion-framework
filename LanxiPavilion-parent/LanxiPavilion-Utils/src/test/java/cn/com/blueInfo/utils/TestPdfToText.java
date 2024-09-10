package cn.com.blueInfo.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestPdfToText {

    @Test
    public void testPdf() {
        File pdfFile = new File("D:\\suxch\\Desktop\\附件：天津市医保支付范围信息维护明细表 (1).pdf");
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String text = pdfTextStripper.getText(document);
            text = text.replace(" ", "_").replace("\n", "+").replace("\r", "-");
            System.out.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
