package com.xzc.dao;

import com.xzc.pojo.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminPermissionDao extends JpaRepository<AdminPermission,Integer> {

    AdminPermission findAllById(int id);
    List<AdminPermission> findAllByIdIn(List<Integer>ids);

}
