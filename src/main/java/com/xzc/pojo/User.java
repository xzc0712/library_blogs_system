package com.xzc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;


@Data
//@Entity注解表示这是一个JPA实体类，定义了该类与数据库表的映射关系。
// 在该注解中可以指定实体类对应的表名、主键生成策略等信息。
@Entity
//@Table注解与@Entity注解一起使用，表示对应的表名时user
// 用于显式指定实体类对应的数据库表名
@Table(name = "user")
//@JsonIgnoreProperties注解是Jackson框架提供的注解，用于过滤掉在序列化和反序列化过程中不需要的属性。
// 在这个例子中，Handler和HibernateLazyInitializer属性在JSON序列化时没有实际意义，
// 使用该注解可以过滤掉这两个属性，使得序列化结果更加简洁和易读。
// 原因：本项目使用jpa做实体类的持久化，在jpa工作过程中会创造代理类来继承User,并添加以下两个无关属性
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User {
    int id;
    String username;
    String password;
    String salt;

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }
}
