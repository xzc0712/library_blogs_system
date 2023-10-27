package com.xzc.dao;

import com.xzc.pojo.AdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRoleDao extends JpaRepository<AdminUserRole,Integer> {
    //根据用户id获取角色id
    List<AdminUserRole>findAllByUid(int uid);

    //删除
    void deleteAllByUid(int uid);
}
