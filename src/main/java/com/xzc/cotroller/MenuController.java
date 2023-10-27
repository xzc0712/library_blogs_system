package com.xzc.cotroller;

import com.xzc.pojo.AdminMenu;
import com.xzc.service.AdminMenuService;
import com.xzc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    AdminMenuService adminMenuService;

    @CrossOrigin
    @GetMapping("api/menu")
    @ResponseBody
    public List<AdminMenu>menu(){
        return adminMenuService.getMuneByCurrentUser();
    }
}
