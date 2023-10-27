package com.xzc.cotroller;

import com.xzc.pojo.User;
import com.xzc.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ListUserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequiresPermissions("api/admin/user")
    @GetMapping("/api/admin/user")
    public List<User>listUser() throws Exception{
        return userService.list();
    }
}
