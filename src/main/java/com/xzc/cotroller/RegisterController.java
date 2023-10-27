package com.xzc.cotroller;

import com.xzc.pojo.User;
import com.xzc.result.Result;
import com.xzc.result.ResultFactory;
import com.xzc.service.UserService;


import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
public class RegisterController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("api/register")
    @ResponseBody
    public Result register(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);

        boolean exist = userService.isExist(username);
        if(exist){
            String msg = "用户名已被占用";
            return ResultFactory.buildFailResult(msg);
        }

        //生成盐,默认长度16为
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        //设置迭代次数
        int times = 2;
        // 得到hash后的密码
        String encodedPassword = new SimpleHash("md5",password,salt,times).toString();
        // 存储用户信息，包括salt与hash后的密码
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userService.add(user);
        return ResultFactory.buildSuccessResult(user);

    }
}
