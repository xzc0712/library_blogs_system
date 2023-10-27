package com.xzc.service;

import com.xzc.dao.AdminUserRoleDao;
import com.xzc.pojo.AdminUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserRoleService {
    @Autowired
    AdminUserRoleDao adminUserRoleDao;

    public List<AdminUserRole> listAllByUid(int uid){
        return adminUserRoleDao.findAllByUid(uid);
    }


}
