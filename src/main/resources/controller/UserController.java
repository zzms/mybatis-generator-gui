package /packageName/.web.controller;

import /packageName/.common.config.Const;
import /packageName/.common.exception.ErrorCode;
import /packageName/.common.exception.ResponseData;
import /packageName/.common.exception.SimpleErrorException;
import /packageName/.dao.entity.User;
import /packageName/.service.IUserService;
import /packageName/.web.view.input.LoginInput;
import /packageName/.web.view.output.mapper.LoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by leosoft on 2018/8/1.
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseData login(@Valid @RequestBody LoginInput loginInput, HttpServletRequest request) throws SimpleErrorException {
        User login = userService.login(loginInput.getUserName(), loginInput.getPassword());
        boolean b = userService.transactionTest();
        if (login == null) {
           return new ResponseData(ErrorCode.ERROR_LOGIN);
        }else {
            LoginOutput loginOutput = new LoginOutput();
            setSessioinValue(request, Const.USER_SESSION,loginOutput);
            return new ResponseData(ErrorCode.SUCCESS);
        }
    }
}
