package com.xzc.service;

import com.xzc.dao.AdminRoleDao;
import com.xzc.pojo.AdminMenu;
import com.xzc.pojo.AdminRole;
import com.xzc.pojo.AdminUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminRoleService {
    @Autowired
    AdminRoleDao adminRoleDao;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    @Autowired
    AdminRoleDao adminRoleDAO;

    //最终目的：获取当前角色的菜单页
    //public List<AdminRole>listWithPermsAndMenus(){
    //    List<AdminRole>roles = findAll();
    //    List<AdminMenu> menus;
    //    for (AdminRole role : roles) {
    //        menus = AdminMenuService.getMenusByRoleId(role.getId());
    //        role.setMenus(menus);
    //    }
    //    return roles;
    //}

    public List<AdminRole> findAll() {
        return adminRoleDao.findAll();
    }

    public void addOrUpdate(AdminRole adminRole) {
        adminRoleDao.save(adminRole);
    }

    public List<AdminRole> listRolesByUser(String username) {
        //获取当前用户uid
        int uid = userService.getByUserName(username).getId();
        //根据uid获取当前用户角色集
        List<AdminUserRole> adminUserRoles = adminUserRoleService.listAllByUid(uid);
        //根据当前角色集获取到对应rids集
        List<Integer> rids = adminUserRoles.stream()
                .map(AdminUserRole::getRid).collect(Collectors.toList());
        //根据对应rids集获取到角色集
        List<AdminRole> roles = adminRoleDAO.findAllById(rids);
        return roles;
    }
}
