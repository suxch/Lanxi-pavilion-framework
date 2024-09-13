package cn.com.blueInfo.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description: Excel工具类
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.util
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:06
 * @Version: 1.0
 */
public class ExcelUtil {
    public static String NO_DEFINE = "no_define";// 未定义的字段
    public static String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";// 默认日期格式
    public static int DEFAULT_COLOUMN_WIDTH = 17;

    /**
     * web导出Excel 标准模板
     * @Title: downloadExcelFile
     * @param fileName 文件名
     * @param headMap 数据列头
     * @param jsonArray 数据集
     * @param response
     * @return void
     * @throws
     */
    public static void downloadExcelFile(String fileName, Map<String, String> headMap, JSONArray jsonArray,
                                         HttpServletResponse response) {
        ExcelUtil.exportWebExcel("CURRENCY", fileName, "", headMap, jsonArray, response);
    }

    /**
     * web导出Excel特价审批系统模板
     * @Title: downloadExcelFilePrice
     * @param: @param fileName
     * @param: @param headMap
     * @param: @param jsonArray
     * @param: @param response
     * @return: void
     * @throws
     */
    public static void downloadExcelFilePrice(String fileName, Map<String, String> headMap, JSONArray jsonArray,
                                              HttpServletResponse response) {
        ExcelUtil.exportWebExcel("PRICE", fileName, "", headMap, jsonArray, response);
    }

    /**
     * 导出前端Excel文件
     * @Title: exportWebExcel
     * @param fileName 文件名
     * @param title 标题名
     * @param baseMap 基础数据表头
     * @param jsonData 数据集
     * @param response 前端返回
     * @return void
     * @throws
     */
    private static void exportWebExcel(String template, String fileName, String title,
                                       Map<String, String> baseMap, JSONArray jsonData, HttpServletResponse response) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            switch (template) {
                case "CURRENCY":
                    ExcelUtil.exportExcelX(title, baseMap, jsonData, os);
                    break;
                case "PRICE":
                    ExcelUtil.exportExcelX(title, baseMap, jsonData, os);
                    break;
                default:
                    ExcelUtil.exportExcelX(title, baseMap, jsonData, os);
                    break;
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "ISO-8859-1"));
            response.setContentLength(content.length);
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出Excel 2007 OOXML (.xlsx)格式
     * 标准模板
     * @param title 标题行
     * @param headMap 属性-列头
     * @param jsonArray 数据集
     * @param out 输出流
     */
    private static void exportExcelX(String title, Map<String, String> headMap, JSONArray jsonArray, OutputStream out) {
        ExcelUtil excel = new ExcelUtil();
        // 产生表格标题行,以及设置列宽
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];

        Iterator<String> baseIter = headMap.keySet().iterator();
        int baseIndex = 0;
        while (baseIter.hasNext()) {
            String fieldName = baseIter.next();
            properties[baseIndex] = fieldName; // 数据的key
            headers[baseIndex] = headMap.get(fieldName); // 显示的列头
            baseIndex++;
        }

        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(-1);// 缓存
        workbook.setCompressTempFiles(true);

        // 标题样式
        CellStyle titleStyle = excel.setTitleStyle(workbook);

        // 表头样式
        CellStyle headerStyle = excel.setHeaderStyle(workbook);

        // 单元格样式
        CellStyle bodyStyle = excel.setBodyStyle(workbook);

        // 单元格样式 - 百分比
        CellStyle percentageStyle = excel.setPercentageStyle(workbook);

        // 单元格样式 - 货币
        CellStyle numericStyle = excel.setNumericStyle(workbook);

        // 单元格样式 - 日期时间
        CellStyle dateTimeStyle = excel.setDateTimeStyle(workbook);

        // 生成一个(带标题)表格
        SXSSFSheet sheet = workbook.createSheet();

        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        if (jsonArray.isEmpty()) {
            // 设置标题行
            if (title != null && !"".equals(title)) {
                excel.setTitleRow(sheet, titleStyle, rowIndex, title, headMap.size(), (short) 36);
                rowIndex++;
            }
            // 设置表头行
            excel.setHeaderRow(sheet, headerStyle, rowIndex, headers, (short) 24);
            rowIndex++;
        } else {
            for (int b_i = 0, b_len = jsonArray.size(); b_i < b_len; b_i++) {
                if (rowIndex == 65535 || rowIndex == 0) {
                    if (rowIndex != 0) {
                        sheet = workbook.createSheet();
                        rowIndex = 0;
                    }
                    // 设置标题行
                    if (title != null && !"".equals(title)) {
                        excel.setTitleRow(sheet, titleStyle, rowIndex, title, headMap.size(), (short) 36);
                        rowIndex++;
                    }
                    // 设置表头行
                    excel.setHeaderRow(sheet, headerStyle, rowIndex, headers, (short) 24);
                    rowIndex++;
                }

                JSONObject oneData = jsonArray.getJSONObject(b_i);
                excel.setRowData(sheet, bodyStyle, percentageStyle, numericStyle, dateTimeStyle,
                        rowIndex, properties, oneData, (short) 18);
                rowIndex++;
            }
        }

        // 自动调整宽度
        for (int i = 0; i < headers.length; i++) {
            sheet.trackAllColumnsForAutoSizing();
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i));
        }

        try {
            workbook.write(out);
            workbook.close();
            workbook.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置标题行
     * @Title: setTitleRow
     * @param sheet
     * @param titleStyle
     * @param rowIndex
     * @param title
     * @param cellLength
     * @param rowHeight
     * @return void
     * @throws
     */
    private void setTitleRow(SXSSFSheet sheet, CellStyle titleStyle, int rowIndex, String title, int cellLength, short rowHeight) {
        SXSSFRow titleRow = sheet.createRow(rowIndex);// 表头 rowIndex=0
        titleRow.setHeightInPoints(rowHeight); // 设置行高
        titleRow.createCell(0).setCellValue(title);
        titleRow.getCell(0).setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cellLength - 1)); //合并单元格
    }

    /**
     * 设置列头行
     * @Title: setHeaderRow
     * @param sheet
     * @param headerStyle
     * @param rowIndex
     * @param rowHeight
     * @return void
     * @throws
     */
    private void setHeaderRow(SXSSFSheet sheet, CellStyle headerStyle, int rowIndex, String[] headers, short rowHeight) {
        SXSSFRow headerRow = sheet.createRow(rowIndex); // 列头 rowIndex = 1
        headerRow.setHeightInPoints(rowHeight);// 设置行高
        for (int h_i = 0, h_len = headers.length; h_i < h_len; h_i++) {
            headerRow.createCell(h_i).setCellValue(headers[h_i]);
            headerRow.getCell(h_i).setCellStyle(headerStyle);
        }
    }

    /**
     * 设置单元格数据
     * @Title: setRowData
     * @param sheet sheet页
     * @param bodyStyle 常规样式
     * @param percentageStyle 百分比样式
     * @param numericStyle 货币样式
     * @param rowIndex 行号
     * @param properties 标题
     * @param oneData 行数据
     * @param rowHeight 行高
     * @return void
     * @throws
     */
    private void setRowData(SXSSFSheet sheet, CellStyle bodyStyle, CellStyle percentageStyle, CellStyle numericStyle, CellStyle dateTimeStyle,
                            int rowIndex, String[] properties, JSONObject oneData, short rowHeight) {
        SXSSFRow dataRow = sheet.createRow(rowIndex);
        dataRow.setHeightInPoints(rowHeight);// 设置行高
        for (int p_i = 0, p_len = properties.length; p_i < p_len; p_i++) {
            Object cellData = oneData.get(properties[p_i]);
            String cellValue = "";
            if (cellData == null)
                cellValue = "";
            else if (cellData instanceof Date)
                cellValue = DateUtil.getFormatDateTime((Date) cellData);
            else if (cellData instanceof Float || cellData instanceof Double)
                cellValue = new BigDecimal(cellData.toString()).setScale(5, BigDecimal.ROUND_HALF_UP).toString();
            else
                cellValue = cellData.toString();

            SXSSFCell newCell = dataRow.createCell(p_i);
            if (this.isNumeric(cellValue)) { // 如果是数值则以小数形式传入
                newCell.setCellValue(Double.parseDouble(cellValue));
                newCell.setCellStyle(numericStyle);
                continue;
            } else if (this.isPercentage(cellValue)) { // 如果是百分比，则以百分比形式传入
                cellValue = cellValue.substring(0, cellValue.indexOf("%"));
                newCell.setCellValue(Double.parseDouble(cellValue) / 100);
                newCell.setCellStyle(percentageStyle);
                continue;
            } else if (DateUtil.getTimeStamp(cellValue) != null) { // 如果是日期，则以日期时间形式传入
                newCell.setCellValue(cellValue);
                newCell.setCellStyle(dateTimeStyle);
                continue;
            } else {
                newCell.setCellValue(cellValue);
                newCell.setCellStyle(bodyStyle);
                continue;
            }
        }
    }

    /**
     * 设置空行
     * @Title: setNullRow
     * @param workbook
     * @param sheet
     * @param rowIndex
     * @param properties
     * @return void
     * @throws
     */
    private void setNullRow(SXSSFSheet sheet, CellStyle bodyStyle, int rowIndex, String[] properties, short rowHeight) {
        SXSSFRow nullDataRow = sheet.createRow(rowIndex);
        nullDataRow.setHeightInPoints(rowHeight);// 设置行高
        for (int p_i = 0, p_len = properties.length; p_i < p_len; p_i++) {
            nullDataRow.createCell(p_i).setCellValue("");
            nullDataRow.getCell(p_i).setCellStyle(bodyStyle);
        }
    }

    /**
     * 设置标题样式
     * @Title: setTitleStyle
     * @param workbook
     * @return CellStyle
     * @throws
     */
    private CellStyle setTitleStyle(SXSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12); //设置字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //设置加粗
        font.setFontName("微软雅黑");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置表头样式
     * @Title: setHeaderStyle
     * @param workbook
     * @return CellStyle
     * @throws
     */
    private CellStyle setHeaderStyle(SXSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 背景颜色
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上边框
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);//设置字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //设置加粗
        font.setFontName("微软雅黑");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置常规单元格样式
     * @Title: setBodyStyle
     * @param workbook
     * @return CellStyle
     * @throws
     */
    private CellStyle setBodyStyle(SXSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 背景颜色
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上边框
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11); //设置字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); //设置加粗
        font.setFontName("微软雅黑");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置百分比单元格样式
     * @Title: setPercentageStyle
     * @param workbook
     * @return CellStyle
     * @throws
     */
    private CellStyle setPercentageStyle(SXSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 背景颜色
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上边框
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11); //设置字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); //设置加粗
        font.setFontName("微软雅黑");
        cellStyle.setFont(font);
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
        return cellStyle;
    }

    /**
     * 设置数字单元格样式
     * @Title: setNumericStyle
     * @param workbook
     * @return CellStyle
     * @throws
     */
    private CellStyle setNumericStyle(SXSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 背景颜色
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上边框
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11); //设置字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); //设置加粗
        font.setFontName("微软雅黑");
        cellStyle.setFont(font);
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        return cellStyle;
    }

    /**
     * 设置数字单元格样式
     * @Title: setCurrencyStyle
     * @param workbook
     * @return CellStyle
     * @throws
     */
    private CellStyle setDateTimeStyle(SXSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 背景颜色
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上边框
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11); //设置字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); //设置加粗
        font.setFontName("微软雅黑");
        cellStyle.setFont(font);
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd hh:mm:ss"));
        return cellStyle;
    }

    /**
     * 判断是否为数字
     * @Title: isNumeric
     * @param number
     * @return boolean
     * @throws
     */
    private boolean isNumeric(String number) {
        if (!StringUtils.isBlank(number)) {
			/*int index = number.indexOf(".");
			if (index < 0) {
				return StringUtils.isNumeric(number);
			} else {
				String num1 = number.substring(0, index);
				String num2 = number.substring(index + 1);
				return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);
			}*/
            return NumberUtils.isNumber(number);
        }
        return false;
    }

    /**
     * 判断是否为百分比
     * @Title: isPercentage
     * @param number
     * @return boolean
     * @throws
     */
    private boolean isPercentage(String number) {
        if (!StringUtils.isBlank(number)) {
            int index = number.indexOf("%");
            if (index > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * @描述：是否是2003的excel，返回true是2003
     * @Title: isExcel2003
     * @param filePath
     * @return boolean
     * @throws
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * @描述：是否是2007的excel，返回true是2007
     * @Title: isExcel2007
     * @param filePath
     * @return boolean
     * @throws
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

}
