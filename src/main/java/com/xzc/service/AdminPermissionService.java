package com.xzc.service;

import com.xzc.dao.AdminPermissionDao;
import com.xzc.dao.AdminRolePermissionDao;
import com.xzc.pojo.AdminPermission;
import com.xzc.pojo.AdminRole;
import com.xzc.pojo.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminPermissionService {
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminRolePermissionDao adminRolePermissionDao;

    @Autowired
    AdminPermissionDao adminPermissionDao;
    /**
     * 实现根据当前用户获取所有权限的方法
     * 不同于返回所有菜单项，这里需要返回Set<String>URLs
     */
    public Set<String>listPermissionURLsByUser(String username){
        List<AdminRole> roles = adminRoleService.listRolesByUser(username);
        //获取角色对应id
        List<Integer> rids = roles.stream().map(AdminRole::getId).collect(Collectors.toList());

        //根据rolePermission交互查找
        List<AdminRolePermission> rolePermissions = adminRolePermissionDao.findAllByRidIn(rids);
        //获取对应permissionID
        List<Integer> pids = rolePermissions.stream().map(AdminRolePermission::getPid).collect(Collectors.toList());

        List<AdminPermission> perms = adminPermissionDao.findAllByIdIn(pids);
        Set<String> URLs = perms.stream().map(AdminPermission::getUrl).collect(Collectors.toSet());

        return URLs;
    }

    /**
     * 判断用户请求接口是否在权限中
     * @param requestAPI
     * @return
     */
    public boolean needFilter(String requestAPI){
        //获取所有permission
        List<AdminPermission> ps = adminPermissionDao.findAll();
        for (AdminPermission p : ps) {
            if (p.getUrl().equals(requestAPI)){
                return true;
            }
        }
        return false;
    }
}
