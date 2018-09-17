package /packageName/.web.filter;

import com.alibaba.fastjson.JSONObject;
import /packageName/.web.aop.AuthorizeWrapper;
import /packageName/.web.controller.BaseController;
import /packageName/.web.view.input.AuthorizeBase;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by leosoft on 2018/8/1.
 */
public class LoginFilter extends BaseController implements Filter {

    private String excludedPages;

    private String[] excludedPageArray;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取不需要拦截的请求
        excludedPages = filterConfig.getInitParameter("exclutions");
        if (/packageName/.common.utils.StringUtil.isNotEmpty(excludedPages)) {
            excludedPageArray = excludedPages.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse hsResponse = (HttpServletResponse)servletResponse;
        AuthorizeWrapper authorizeWrapper = new AuthorizeWrapper(hsRequest);
        String body = authorizeWrapper.getBody();
        AuthorizeBase authorizeBase = JSONObject.parseObject(body, AuthorizeBase.class);

        //过滤需要过滤的请求
        boolean isExcludedPage = false;
        for (String page : excludedPageArray) {
            String relativePath = /packageName/.common.utils.RequestUtil.getRelativePath(hsRequest);
            if(page.equalsIgnoreCase(relativePath)){
                isExcludedPage = true;
                break;
            }
            //后缀
            String excludedSubfix = relativePath.substring(relativePath.lastIndexOf(".") + 1);
            String pageSubfix = page.substring(page.lastIndexOf(".") + 1);
            if(excludedSubfix.equalsIgnoreCase(pageSubfix)){
                isExcludedPage = true;
                break;
            }
        }
        if(isExcludedPage){
            chain.doFilter(authorizeWrapper, hsResponse);
        }else{
                    try {
                        if (getSessionValue(hsRequest, /packageName/.common.config.Const.USER_SESSION) == null) {
                            hsResponse.sendRedirect(hsResponse.encodeURL(/packageName/.common.utils.RequestUtil.getBasePath(hsRequest)+"user/login"));
                        }
                        else {
                            chain.doFilter(authorizeWrapper,hsResponse);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
        }
    }

    @Override
    public void destroy() {

    }
}
