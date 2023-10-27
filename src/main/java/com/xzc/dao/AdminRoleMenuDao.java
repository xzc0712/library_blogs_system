package com.xzc.dao;

import com.xzc.pojo.AdminRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRoleMenuDao extends JpaRepository<AdminRoleMenu,Integer> {

    //根据List<rids>查找
    // List<AdminRoleMenu>findAllByRid(List<Integer>rids);
    List<AdminRoleMenu> findAllByRidIn(List<Integer>rids);


    //根据单个rid查找menu
    List<AdminRoleMenu>findAllByRid(int rid);

    //删除
    void deleteAllByRid(int rid);
}
