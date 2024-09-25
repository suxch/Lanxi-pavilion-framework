package cn.com.blueInfo.core.baseService.timedTask;

import cn.com.blueInfo.utils.DateUtil;
import cn.com.blueInfo.utils.entity.DataBaseParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

/**
 * mysql定时备份
 * @ClassName: MySqlDump
 * @author suxch
 * @date 2018年5月13日  上午4:01:47
 */
@Log4j2
public class MySqlDump extends TimerTask {

    private static DataBaseParam dataBaseParam;

    public MySqlDump(DataBaseParam dataBaseParam) {
        MySqlDump.dataBaseParam = dataBaseParam;
    }

    @Override
    public void run() {
        this.exportMySqlDumpFile();
    }

    protected void exportMySqlDumpFile() {
        if (!dataBaseParam.getDriverClassName().contains("mysql")) {
            return;
        }
        Runtime runtime = Runtime.getRuntime();
        String command = getExportCommand();
        try {
            runtime.exec(command);
            log.info("数据库备份成功");
        } catch (IOException e) {
            throw new RuntimeException("数据库备份失败", e);
        }
    }

    private static String getExportCommand() {
        StringBuffer command = new StringBuffer();
        String username = dataBaseParam.getUsername();// 用户名
        String password = dataBaseParam.getPassword();// 用户密码
        String exportDatabaseName = dataBaseParam.getDatabase();// 需要导出的数据库名
        String host = dataBaseParam.getHost();// 从哪个主机导出数据库，如果没有指定这个值，则默认取localhost
        String port = dataBaseParam.getPort();// 使用的端口号
        String folderPath = dataBaseParam.getDumpPath(); // 导出的文件夹路径
        File folder = new File(folderPath);
        if (!folder.exists())
            folder.mkdirs();
        String fileName = DateUtil.getFormatDate(new Date());
        String exportPath = folderPath + fileName + ".sql";// 导出路径

        // 注意哪些地方要空格，哪些不要空格
        command.append("mysqldump -u").append(username).append(" -p").append(password)// 密码是用的小p，而端口是用的大P。
                .append(" -h").append(host).append(" -P").append(port).append(" ").append(exportDatabaseName)
                .append(" -r ").append(exportPath);
        return command.toString();
    }
}
