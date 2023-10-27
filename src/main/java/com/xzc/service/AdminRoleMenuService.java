package com.xzc.service;


import com.xzc.dao.AdminRoleMenuDao;
import com.xzc.pojo.AdminRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Evan
 * @date 2019/11
 */
@Service
public class AdminRoleMenuService {
    @Autowired
    AdminRoleMenuDao adminRoleMenuDao;

    public List<AdminRoleMenu> findAllByRid(int rid) {
        return adminRoleMenuDao.findAllByRid(rid);
    }

    public List<AdminRoleMenu> findAllByRid(List<Integer> rids) {
        return adminRoleMenuDao.findAllByRidIn(rids);
    }

    public void save(AdminRoleMenu rm) {
        adminRoleMenuDao.save(rm);
    }

}
