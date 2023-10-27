package com.xzc.cotroller;

import com.xzc.pojo.User;
import com.xzc.result.Result;
import com.xzc.result.ResultFactory;
import com.xzc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    UserService userService;


    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser , HttpSession session) {
        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(username);
        } catch (AuthenticationException e) {
            String message = "账号密码错误";
            return ResultFactory.buildFailResult(message);
        }


        //*************未使用shiro(简单版)*************

        //对html 标签进行转义，防止XSS攻击
        //String username = requestUser.getUsername();
        //username = HtmlUtils.htmlEscape(username);
        //
        //User user = userService.get(username, requestUser.getPassword());
        //if (null == user) {
        //    //为空,不存在
        //    String msg = "用户名已被占用";
        //    return ResultFactory.buildFailResult(msg);
        //} else {
        //    session.setAttribute("user",user);
        //    String msg = "用户名已被占用";
        //    return ResultFactory.buildSuccessResult(msg);
        //}


        // ************原始*************

        //if (!Objects.equals("admin", username) || (!Objects.equals("123456", requestUser.getPassword()))) {
        //    String message = "账号密码错误";
        //    System.out.println("===账号密码错误===");
        //    return new Result(400);
        //}else {
        //    System.out.println("===账号密码正确===");
        //    return new Result(200);
        //}
    }
}
