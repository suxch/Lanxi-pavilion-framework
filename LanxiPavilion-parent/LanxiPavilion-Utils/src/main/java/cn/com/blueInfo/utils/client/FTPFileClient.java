package cn.com.blueInfo.utils.client;

import cn.com.blueInfo.utils.entity.FTPParam;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.SocketException;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description: TODO
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.client
 * @Author: suxch
 * @CreateTime: 2024/8/13 22:41
 * @Version: 1.0
 */
public class FTPFileClient {

    private static final Logger logger = LoggerFactory.getLogger(FTPFileClient.class);

    private static final FTPParam ftpParam = new FTPParam();
    /** FTP地址 */
    private static final String host = ftpParam.getHost();
    /** FTP端口号 */
    private static final Integer port = ftpParam.getPort();
    /** FTP用户名 */
    private static final String userName = ftpParam.getUserName();
    /** FTP密码 */
    private static final String password = ftpParam.getPassword();
    /** FTP流程文件物理路径 */
    protected static final String path = ftpParam.getPath();
    /** FTP手机端更新文件物理路径 */
    protected static final String appPath = ftpParam.getAppPath();

    /**
     * 文件上传
     * @Title: uploadFile
     * @param fileName
     * @param input
     * @return boolean
     * @throws
     */
    public static boolean uploadFile(String fileName, InputStream input) {
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient();
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(path);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("GBK");
            fileName = new String(fileName.getBytes("GBK"), "iso-8859-1"); // 中文支持
            boolean result = ftpClient.storeFile(fileName, input);
            System.out.println(result);
            input.close();
            ftpClient.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * 文件上传
     * @Title: uploadFile
     * @author xichen.su
     * @param fileName
     * @param input
     * @return boolean
     * @throws
     */
    public static boolean uploadFile(String fileName, String subPath, InputStream input) {
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient();
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            subPath = subPath.indexOf("/") == 0 ? subPath : "/" + subPath;
            ftpClient.changeWorkingDirectory(path + subPath);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("GBK");
            fileName = new String(fileName.getBytes("GBK"), "iso-8859-1"); // 中文支持
            boolean result = ftpClient.storeFile(fileName, input);
            System.out.println(result);
            input.close();
            ftpClient.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * 文件下载
     * @Title: downloadFtpFile
     * @param response
     * @param fileName
     * @return long
     * @throws
     */
    public static long downloadFile(HttpServletResponse response, String fileName) {
        long count = 0;
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(path);
            InputStream is = ftpClient.retrieveFileStream(new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            if (is != null) {
                ServletOutputStream os = response.getOutputStream();
                if (fileName.indexOf("=") == 13) {
                    fileName = fileName.substring(fileName.indexOf("=") + 1);
                }
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
                response.setContentType("multipart/form-data");
                logger.info("ServletOutputStream>>> " + os);
                byte[] bytes = new byte[1024];
                int readBye;
                while ((readBye = is.read(bytes)) != -1) {
                    os.write(bytes, 0, readBye);
                    count += readBye;
                }
                os.flush();
                is.close();
                os.close();
            }
            ftpClient.logout();

        } catch (FileNotFoundException e) {
            System.out.println("没有找到" + path + "文件");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (SocketException e) {
            System.out.println("连接FTP失败.");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            System.out.println("文件读取错误。");
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return count;

    }

    /**
     * 通过文件名下载文件
     * @Title: downloadFileByName
     * @param response
     * @param fileName
     * @return void
     * @throws
     */
    public static void downloadFileByName(HttpServletResponse response, String filePath, String fileName) {
        ServletOutputStream outputStream = null;
        InputStream is = null;
        filePath = (filePath != null && !"".equals(filePath)) ? filePath : appPath;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            FTPClient ftp = getFTPClient();
            ftp.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh"); // 中文支持
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();// ftp client告诉ftp server开通一个端口来传输数据
            ftp.changeWorkingDirectory(appPath);
            ftp.retrieveFile(new String(fileName.getBytes("GBK"), "ISO-8859-1"), os);
            byte[] content = os.toByteArray();
            is = new ByteArrayInputStream(content);

            // 设置response参数，可以打开下载页面
            response.reset();

            response.setContentType("applicationnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            response.setContentLength(content.length);

            outputStream = response.getOutputStream();
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {
                outputStream.write(buff, 0, bytesRead);
            }
            ftp.logout();
        } catch (FileNotFoundException e) {
            System.out.println("没有找到" + appPath + "文件");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (SocketException e) {
            System.out.println("连接FTP失败.");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            System.out.println("文件读取错误。");
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            System.out.println("执行");
            try {
                outputStream.flush();
                is.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件大小
     * @Title: getFileSize
     * @param filePath
     * @param subFilePath
     * @param fileName
     * @return Long
     * @throws
     */
    public static Long getFileSize(String filePath, String subFilePath, String fileName) {
        filePath = (filePath != null && !"".equals(filePath)) ? filePath : path;
        Long fileSize = -1L;
        try {
            FTPClient ftp = getFTPClient();
            ftp.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh"); // 中文支持
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();// ftp client告诉ftp server开通一个端口来传输数据
            ftp.changeWorkingDirectory(filePath + subFilePath);
            FTPFile[] files = ftp.listFiles(fileName);
            if (files != null && files.length > 0) {
                fileSize = files[0].getSize();
            }
            ftp.logout();
        } catch (FileNotFoundException e) {
            System.out.println("没有找到" + path + "文件");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (SocketException e) {
            System.out.println("连接FTP失败.");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            System.out.println("文件读取错误。");
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return fileSize;
    }

    /**
     * 通过文件名下载文件
     * @Title: downloadFileByName
     * @param response
     * @param fileName
     * @return void
     * @throws
     */
    public static void downloadFileByName(HttpServletResponse response, String filePath, String subFilePath, String fileName) {
        ServletOutputStream outputStream = null;
        InputStream is = null;
        filePath = (filePath != null && !"".equals(filePath)) ? filePath : path;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            FTPClient ftp = getFTPClient();
            ftp.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh"); // 中文支持
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();// ftp client告诉ftp server开通一个端口来传输数据
            ftp.changeWorkingDirectory(filePath + subFilePath);
            ftp.retrieveFile(new String(fileName.getBytes("GBK"), "ISO-8859-1"), os);
            byte[] content = os.toByteArray();
            is = new ByteArrayInputStream(content);

            // 设置response参数，可以打开下载页面
            response.reset();

            response.setContentType("applicationnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            response.setContentLength(content.length);

            outputStream = response.getOutputStream();
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {
                outputStream.write(buff, 0, bytesRead);
            }
            ftp.logout();
        } catch (FileNotFoundException e) {
            System.out.println("没有找到" + path + "文件");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (SocketException e) {
            System.out.println("连接FTP失败.");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            System.out.println("文件读取错误。");
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            System.out.println("执行");
            try {
                outputStream.flush();
                is.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 图片文件展示
     * @Title: imageFileViewByName
     * @param response
     * @param fileName
     * @return void
     * @throws
     */
    public static void imageFileViewByName(HttpServletResponse response, String filePath, String fileName) {
        FTPClient ftpClient = null;
        filePath = (filePath != null && !"".equals(filePath)) ? filePath : path;
        try {
            ftpClient = FTPFileClient.getFTPClient();
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(path);
            InputStream is = ftpClient.retrieveFileStream(new String(fileName.getBytes("GBK"), "ISO-8859-1"));

            response.setContentType("image/jpeg");
            ServletOutputStream sos = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                sos.write(bytes, 0, len);
            }
            is.close();
            sos.flush();
            sos.close();

            ftpClient.logout();

        } catch (FileNotFoundException e) {
            System.out.println("没有找到" + path + "文件");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (SocketException e) {
            System.out.println("连接FTP失败.");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            System.out.println("文件读取错误。");
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * 图片文件展示
     * @Title: imageFileViewByName
     * @author xichen.su
     * @param response
     * @param fileName
     * @return void
     * @throws
     */
    public static void imageFileViewByName(HttpServletResponse response, String filePath, String subFilePath, String fileName) {
        FTPClient ftpClient = null;
        filePath = (filePath != null && !"".equals(filePath)) ? filePath : path;
        subFilePath = subFilePath.indexOf("/") == 0 ? subFilePath : "/" + subFilePath;

        ServletOutputStream sos = null;
        BufferedImage buImg = null;
        InputStream is = null;
        try {
            ftpClient = FTPFileClient.getFTPClient();
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            filePath = filePath + subFilePath;
            filePath = new String(filePath.getBytes("GBK"), "ISO-8859-1");
            ftpClient.changeWorkingDirectory(filePath);
            is = ftpClient.retrieveFileStream(new String(fileName.getBytes("GBK"), "ISO-8859-1"));

            String imgType = fileName.substring(fileName.lastIndexOf(".") + 1);
            response.setContentType("image/" + imgType);

            sos = response.getOutputStream();
            buImg = ImageIO.read(is);
            ImageIO.write(buImg, imgType, sos);

        } catch (FileNotFoundException e) {
            System.out.println("没有找到" + filePath + subFilePath + "文件");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (SocketException e) {
            System.out.println("连接FTP失败.");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            System.out.println("文件读取错误。");
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            try {
                sos.flush();
                sos.close();
                buImg.flush();
                is.close();
                ftpClient.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量文件下载
     * @Title: batchDownloadFile
     * @param response
     * @param fileNameList
     * @return long
     * @throws
     */
    public static long batchDownloadFile(HttpServletResponse response, List<String> fileNameList) {
        long count = 0;
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(path);
            InputStream is = null;
            ZipOutputStream zout = null;
            response.setHeader("Content-type", "application-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("附件.zip".getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("multipart/form-data");
            CheckedOutputStream checksum = new CheckedOutputStream(response.getOutputStream(), new Adler32());
            zout = new ZipOutputStream(checksum);
            for (int i = 0; i < fileNameList.size(); i++) {
                String fileName = fileNameList.get(i);
                is = ftpClient.retrieveFileStream(new String(fileName.getBytes("GBK"), "ISO-8859-1"));
                if (fileName.indexOf("=") == 13) {
                    fileName = fileName.substring(fileName.indexOf("=") + 1);
                }
                zout.putNextEntry(new ZipEntry(i + ". " + fileName));
                int length;
                byte[] buffer = new byte[1024];
                while ((length = is.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                    count += length;
                }
                is.close();
                ftpClient.completePendingCommand();
            }
            zout.closeEntry();
            zout.close();
            ftpClient.logout();

        } catch (FileNotFoundException e) {
            System.out.println("没有找到" + path + "文件");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (SocketException e) {
            System.out.println("连接FTP失败.");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            System.out.println("文件读取错误。");
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return count;
    }

    /**
     * 获取FTP连接
     * @Title: getFTPClient
     * @return FTPClient
     * @throws
     */
    public static FTPClient getFTPClient() {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(host, port);// 连接FTP服务器
            ftpClient.login(userName, password);// 登陆FTP服务器
            ftpClient.doCommand("OPTS", "UTF8 OFF");
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP,用户名或密码错误!");
                ftpClient.disconnect();
            } else {
                logger.info("FTP连接成功!");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

}
