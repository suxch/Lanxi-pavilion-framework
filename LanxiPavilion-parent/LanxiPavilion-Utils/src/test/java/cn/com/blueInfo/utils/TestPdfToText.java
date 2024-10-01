package cn.com.blueInfo.utils;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Optional;

public class TestPdfToText {

    /**
     * 提取标准PDF文件中的文字信息，目前测试excel转成的pdf 提取文字是可以的，但存在严重的换行问题
     */
    @Test
    public void testPdf() {
        User user = getUser();

        long startTime_nano1 = System.nanoTime();
        if (null != user) {
            if (user.getDept() != null) {
                String deptName1 = user.getDept().getDeptName();
                if (StringUtils.isBlank(deptName1)) {
                    System.out.println("未指定部门");
                } else {
                    System.out.println(deptName1);
                }
            } else {
                System.out.println("未指定部门");
            }
        }
        long endTime_nano1 = System.nanoTime();
        System.out.println((endTime_nano1 - startTime_nano1) / 1000 + "微秒");
        System.out.println(((endTime_nano1 - startTime_nano1) / 1000) / 1000 + "毫秒");

        long startTime_nano = System.nanoTime();
        String deptName = Optional.of(user)
                .map(User::getDept)
                .map(Dept::getDeptName).filter(StringUtils::isNotBlank)
                .orElse("未指定部门");
        System.out.println(deptName);
        long endTime_nano = System.nanoTime();
        System.out.println((endTime_nano - startTime_nano) / 1000 + "微秒");
        System.out.println(((endTime_nano - startTime_nano) / 1000) / 1000 + "毫秒");

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

    private User getUser() {
        Dept dept = new Dept();
        dept.setDeptNumber(123);

        User user = new User();
        user.setUsername("苏希辰");
        user.setAge(33);
        user.setPost("程序员");
        user.setDept(dept);
        return user;
    }

}
@Data
class User {
    private String username;
    private Integer age;
    private String post;
    private Dept dept;
}

@Data
class Dept {
    private String deptName;
    private Integer deptNumber;
}
