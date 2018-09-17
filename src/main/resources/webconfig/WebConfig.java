package /packageName/;

import /packageName/.web.filter.LoginFilter;
import /packageName/.web.filter.XssAndSqlFilter;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by leosoft on 2018/8/1.
 */
@Configuration
public class WebConfig {

    @Bean
    public RemoteIpFilter remoteIpFilter(){
        return new RemoteIpFilter();
    }

    @Bean
    @Order(1)
    public FilterRegistrationBean loginFilterBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        String exclutions="/home/pageNotFound,/home/error,/user/login,/user/logout,/css/,/js/,/images/,*.css,*.js,*.jpg,*.png,*.gif,*.ico";
        filterRegistrationBean.addInitParameter("exclutions",exclutions);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("loginFilter");
        filterRegistrationBean.setFilter(new LoginFilter());
        return filterRegistrationBean;
    }


    @Bean
    @Order(2)
    public FilterRegistrationBean xssAndSqlFilterBean(){
        FilterRegistrationBean<XssAndSqlFilter> xssAndSqlFilterFilterRegistrationBean = new FilterRegistrationBean(new XssAndSqlFilter());
        xssAndSqlFilterFilterRegistrationBean.addUrlPatterns("/*");
        return xssAndSqlFilterFilterRegistrationBean;
    }


}
