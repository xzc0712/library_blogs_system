package com.xzc.dao;

import com.xzc.pojo.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRoleDao extends JpaRepository<AdminRole,Integer> {
    //根据id获取角色信息
    AdminRole findById(int id);
    //根据username获取角色信息
    AdminRole findAllByName(String name);
}
