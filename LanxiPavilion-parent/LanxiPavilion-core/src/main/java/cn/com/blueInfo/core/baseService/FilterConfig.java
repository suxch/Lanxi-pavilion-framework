package cn.com.blueInfo.core.baseService;

import cn.com.blueInfo.core.baseService.filters.RequestFilter;
import cn.com.blueInfo.core.baseService.filters.VisitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义过滤器配置
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestFilter> firstFilter() {
        FilterRegistrationBean<RequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestFilter());
        registrationBean.addUrlPatterns("/*");    // 所有请求都拦截
        registrationBean.setName("firstFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<VisitFilter> secondFilter() {
        FilterRegistrationBean<VisitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new VisitFilter());
        registrationBean.addUrlPatterns("/*");    // 所有请求都拦截
        registrationBean.setName("secondFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
