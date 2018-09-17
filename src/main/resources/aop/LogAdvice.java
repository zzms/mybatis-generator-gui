package /packageName/.web.aop;

import /packageName/.web.controller.BaseController;
import /packageName/.web.view.output.LoginOutput;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by leosoft on 2018/8/8.
 */
@Component
@Aspect
@Slf4j
public class LogAdvice extends BaseController {
    @Around( "within(/packageName/.web.controller..*)")
     public Object runAroundPoint(ProceedingJoinPoint joinpoint) throws Exception {
        long beginTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //访问的url
        String url = request.getRequestURL().toString();
        //post or get请求
        String method = request.getMethod();
        //远程ip
        String remoteAddr = /packageName/.common.utils.RequestUtil.getIpAddress(request);

        if (getSessionValue(request, /packageName/.common.config.Const.USER_SESSION) != null) {
            //访问用户
            LoginOutput user= (LoginOutput) getSessionValue(request, /packageName/.common.config.Const.USER_SESSION);
        }
        Object result = null;
        Object[] args = joinpoint.getArgs();
        try{
            result = joinpoint.proceed(args);
            long endTime = System.currentTimeMillis();

            //todo 这里写日志到数据库

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }


    @Before("within(/packageName/.web.controller..*)")
    public void doBefore(JoinPoint joinPoint){

    }

}
