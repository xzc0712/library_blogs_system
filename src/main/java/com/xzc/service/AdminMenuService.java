package com.xzc.service;

import com.xzc.dao.AdminMenuDao;
import com.xzc.pojo.AdminMenu;
import com.xzc.pojo.AdminRoleMenu;
import com.xzc.pojo.AdminUserRole;
import com.xzc.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuService {

    @Autowired
    AdminMenuDao adminMenuDao;
    @Autowired
    UserService userService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminMenu> getMuneByCurrentUser() {
        //从数据库中获取当前用户
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.getByUserName(username);

        //获取当前用户对应的所有角色id列表
        List<AdminUserRole> userRoles = adminUserRoleService.listAllByUid(user.getId());
        //**简化列表的处理，包括使用map提取集合中的某一属性
        List<Integer> rids = userRoles.stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        // 查询出这些角色对应的所有菜单项
        //1. 根据角色的id查询对应的菜单项id
        List<AdminRoleMenu> roleMenus = adminRoleMenuService.findAllByRid(rids);
        List<Integer> menusId = roleMenus.stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        //2. 根据上述的菜单id查询所有菜单项,distinct()用于去重复
        List<AdminMenu> menus = adminMenuDao.findAllById(menusId).stream().distinct().collect(Collectors.toList());

        // 处理菜单表结构,这里获取到的是所有节点吧？
        handleMenus(menus);
        return menus;
    }

    public List<AdminMenu> getAllByParentId(int id) {
        List<AdminMenu> children = adminMenuDao.findAllByParentId(id);
        return children;
    }

    /**
     * 查询树结构的方法
     * 1. 遍历菜单项，根据每一项的id查询出所有的子项，放入children属性
     * 2. 剔除所有子项，只保留第一层的父节点
     * 如果数据量特别大：应该考虑按节点动态加载，监听节点的展开事件发送节点id作为参数查询出所有子节点
     *
     * @param menus
     */
    public void handleMenus(List<AdminMenu> menus) {
        for (AdminMenu menu : menus) {
            //如果有父节点则作为孩子
            List<AdminMenu> children = getAllByParentId(menu.getId());
            menu.setChildren(children);
        }
        Iterator<AdminMenu> iterator = menus.iterator();
        while (iterator.hasNext()) {
            AdminMenu menu = iterator.next();
            if (menu.getParentId() != 0) {
                iterator.remove();
            }
        }
    }

    /**
     * lambda表达式
     */
    public void handleMenus2(List<AdminMenu> menus) {
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            m.setChildren(children);
        });

        menus.removeIf(m -> m.getParentId() != 0);
    }
}
