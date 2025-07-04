package cn.com.blueInfo.utils.filter;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 自定义日志过滤器
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.filter
 * @Author: suxch
 * @CreateTime: 2024/8/14 23:11
 * @Version: 1.0
 */
public class LogFilter extends OncePerRequestFilter implements Ordered {

    private static final String MDC_TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String traceIDStr = getMdcTraceId();
        MDC.put(MDC_TRACE_ID, traceIDStr);
        filterChain.doFilter(request, response);
        MDC.clear();
    }

    private String getMdcTraceId() {
        long currentTime = System.nanoTime();
        return String.join("_", MDC_TRACE_ID, String.valueOf(currentTime));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
