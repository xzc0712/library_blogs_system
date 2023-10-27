package com.xzc.Realm;

import com.xzc.pojo.User;
import com.xzc.service.AdminPermissionService;
import com.xzc.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class WJRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    AdminPermissionService adminPermissionService;

    /**
     * 简单重写获取授权信息方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前用户的所有权限
        String username = principalCollection.getPrimaryPrincipal().toString();
        Set<String> perms = adminPermissionService.listPermissionURLsByUser(username);
        //将权限放入授权信息中
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        s.setStringPermissions(perms);
        return s;
    }

    /**
     * 获取认证信息，即根据token中的用户名从数据库中获取密码、盐等并返回
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        User user = userService.getByUserName(username);
        String password = user.getPassword();
        String salt = user.getSalt();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, ByteSource.Util.bytes(salt), getName());
        return authenticationInfo;
    }
}
