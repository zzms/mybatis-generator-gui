package com.css.platform.web.controller;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by leosoft on 2018/8/6.
 */
@Controller
public class BaseController {


    /**
     * 设置值到session里
     *
     * @param request
     * @param key
     * @param value
     */
    public void setSessioinValue(HttpServletRequest request, String key,
                                 Object value) {
        request.getSession().setAttribute(key, value);
    }

    /**
     * 获取session值
     *
     * @param request
     * @param key
     */
    public Object getSessionValue(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }



    public boolean checkLogin(HttpServletRequest request) {
        return false;
    }
}
