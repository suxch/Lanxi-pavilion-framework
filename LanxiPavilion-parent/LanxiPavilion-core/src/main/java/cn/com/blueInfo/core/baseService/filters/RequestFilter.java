package cn.com.blueInfo.core.baseService.filters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: wangsaichao
 * @date: 2021/8/12 14:52
 * @description: 请求过滤器   用于处理请求参数的校验
 */
@WebFilter(filterName = "requestFilter", urlPatterns = "/*")
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
                filterConfig.getServletContext());
        System.out.println("----RequestFilter初始化----");
    }

    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Object requestUserName = null;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        httpRequest.setCharacterEncoding("utf-8");
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String url = httpRequest.getRequestURI();
        Integer userIndex = url.indexOf("selectUserInfoByLoginName");
        String requestParam = servletRequest.getParameter("requestParam");
        JSONObject jso = JSON.parseObject(requestParam);
        try{
            requestUserName = jso.get("requestUserName");
        }catch(NullPointerException e){
            throw new ServletException("请求用户名不能为空");
        }
        if(!userIndex.toString().equals("-1")){
            filterChain.doFilter(httpRequest, httpResponse);
        }
        else if(httpRequest.getHeader("Authorization")!=null){
            String decorderMessage = httpRequest.getHeader("Authorization");
            filterChain.doFilter(httpRequest, httpResponse);
            System.out.println(decorderMessage);
        }
        else if(requestUserName!=null){
            filterChain.doFilter(httpRequest, httpResponse);
        }
    }

    @Override
    public void destroy() {
        System.out.println("----RequestFilter销毁----");
    }

}
