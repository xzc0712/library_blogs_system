package com.xzc.dao;

import com.xzc.pojo.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminMenuDao extends JpaRepository<AdminMenu,Integer> {
    //根据单个菜单项id查找可访问的菜单
    AdminMenu findById(int id);

    //根据父节点id查找子节点
    List<AdminMenu> findAllByParentId(int parentId);

}
