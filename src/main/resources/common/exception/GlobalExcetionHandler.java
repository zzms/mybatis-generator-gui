package /packageName/.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by leosoft on 2018/8/3.
 */
@ControllerAdvice
@Slf4j
public class GlobalExcetionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public <T> ResponseData<T> methodArgumentNotValidErrorHandler(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        ResponseData<T> responseData = new ResponseData();
        if (ex instanceof MethodArgumentNotValidException) {
            BindingResult result = ((MethodArgumentNotValidException)ex).getBindingResult();
            final List<FieldError> fieldErrors = result.getFieldErrors();
            if (fieldErrors.size()>0) {
                responseData=new ResponseData(fieldErrors.get(0).getDefaultMessage());
            }
        }
        return responseData;
    }


    @ExceptionHandler(/packageName/.common.exception.SimpleErrorException.class)
    @ResponseBody
    public <T> ResponseData<T> SimpleErrorExceptionHandler(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        ResponseData<T> responseData = new ResponseData();
        if (ex instanceof /packageName/.common.exception.SimpleErrorException) {
            responseData = new ResponseData(((/packageName/.common.exception.SimpleErrorException) ex).getErrorCode());
        }
        return responseData;
    }


    @ExceptionHandler
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception ex){

        ex.printStackTrace();
        ModelAndView modelAndView = new ModelAndView("redirect:/home/pageNotFound");
        if (ex instanceof HttpRequestMethodNotSupportedException) {
           return modelAndView;
       } else {
            modelAndView = new ModelAndView("redirect:/home/error");
            // 其他错误
           return modelAndView;
        }

    }
}
