package /packageName/.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by leosoft on 2018/8/1.
 * 过滤XSS攻击和Sql注入攻击
 */
public class XssAndSqlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /packageName/.common.utils.XssAndSqlHttpServletRequestWrapper xssRequest = new /packageName/.common.utils.XssAndSqlHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(xssRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
