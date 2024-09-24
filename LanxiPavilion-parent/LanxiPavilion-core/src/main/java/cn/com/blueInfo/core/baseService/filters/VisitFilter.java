package cn.com.blueInfo.core.baseService.filters;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * 访问过滤器
 * @date 2018-07-10 16:00:00
 * @author Administrator
 */
public class VisitFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
                filterConfig.getServletContext());
        System.out.println("----VisitFilter初始化----");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        String uri = httpRequest.getRequestURI();
        String type = httpRequest.getHeader("X-Requested-With") == null ? ""
                : httpRequest.getHeader("X-Requested-With");// XMLHttpRequest
        String context = httpRequest.getContextPath();
        String loginUrl = context + "/login/loginUser/userBase.do";

        try {
            if (uri.indexOf(".do") > 0) {
                String[] uriArr = uri.split("/");
                String path = uriArr[2];
                if ("login".equals(path)) {
                    filterChain.doFilter(httpRequest, httpResponse); // 执行目标资源，放行
                } else {
                    String sessionId = session.getId();
                    String loginInfoStr = (String) session.getAttribute("loginInfo");
                    if (loginInfoStr == null) {
                        if ("XMLHttpRequest".equals(type)) {
                            httpResponse.setHeader("SESSIONSTATUS", "TIMEOUT");
                            httpResponse.setHeader("CONTEXTPATH", loginUrl);
                            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        } else {
                            httpResponse.sendRedirect(loginUrl);
                        }
                        return;
                    }
                    JSONObject sessionLoginInfo = JSONObject.parseObject(URLDecoder.decode(loginInfoStr, "UTF-8"));

                    Cookie[] cookies = httpRequest.getCookies();
                    String cookieSessionId = "";
                    JSONObject cookieLoginInfo = null;
                    for (Cookie cookie: cookies) {
                        if ("JSESSIONID".equals(cookie.getName()))
                            cookieSessionId = cookie.getValue();
                        if ("loginInfo".equals(cookie.getName())) {
                            String loginInfoValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                            cookieLoginInfo = JSONObject.parseObject(loginInfoValue);
                        }
                    }

                    String sessionLoginName = sessionLoginInfo.getString("loginName");
                    String cookieLoginName = cookieLoginInfo.getString("loginName");

                    if (sessionId.equals(cookieSessionId) && sessionLoginName.equals(cookieLoginName)) {
                        filterChain.doFilter(httpRequest, httpResponse); // 执行目标资源，放行
                    } else {
                        if ("XMLHttpRequest".equals(type)) {
                            httpResponse.setHeader("SESSIONSTATUS", "TIMEOUT");
                            httpResponse.setHeader("CONTEXTPATH", loginUrl);
                            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        } else {
                            httpResponse.sendRedirect(loginUrl);
                        }
                        return;
                    }

                    // Map<String, Object> paramMap = new HashMap<String, Object>();
                    // Integer configList = serverConfigMapper.queryServerConfigCount(paramMap);
                    // System.out.println(configList);
                    // chain.doFilter(httpRequest, httpResponse); // 执行目标资源，放行
                }
            } else {
                filterChain.doFilter(httpRequest, httpResponse); // 执行目标资源，放行
            }
        } catch (IOException e) {
            throw new IOException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void destroy() {
        System.out.println("----VisitFilter销毁----");
    }

}
