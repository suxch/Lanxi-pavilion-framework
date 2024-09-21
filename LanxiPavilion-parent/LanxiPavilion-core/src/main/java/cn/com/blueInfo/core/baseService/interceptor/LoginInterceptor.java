package cn.com.blueInfo.core.baseService.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * description: 登录拦截器
 * @author: wangxing
 * @date: 2019/7/10 14:53
*/

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();
        if(requestURI.indexOf("editClientIfo.action")>0){
            //说明处在编辑的页面
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            if(username!=null){
                //登陆成功的用户
                return true;
            }else{
                //没有登陆，转向登陆界面
                try {
                    request.getRequestDispatcher("/login.jsp").forward(request,response);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new IOException(e);
                }
                return false;
            }
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle，确定下执行时机");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        System.out.println("afterCompletion，确定下执行时机");
    }
}
