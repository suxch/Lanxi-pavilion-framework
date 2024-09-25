package cn.com.blueInfo.core.baseService.listeners;

import cn.com.blueInfo.core.baseService.service.TimeManagerService;
import cn.com.blueInfo.utils.entity.DataBaseParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听容器
 * @ClassName: TomcatListener
 * @author suxch
 * @date 2018年3月23日  上午5:17:27
 */
@WebListener
public class TomcatListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("tomcat启动了。。。。。。");
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        DataBaseParam dataBaseParam = null;
        if (ctx != null) {
            dataBaseParam = ctx.getBean(DataBaseParam.class);
        }
        // 启动定时任务
        if (dataBaseParam!= null) {
            System.out.println("dataBaseParam:" + dataBaseParam.toString());
            new TimeManagerService(dataBaseParam);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("tomcat关闭了。。。。。。");
        ServletContextListener.super.contextDestroyed(sce);
    }

}
