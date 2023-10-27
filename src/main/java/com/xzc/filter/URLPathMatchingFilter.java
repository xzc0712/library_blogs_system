package com.xzc.filter;

import com.xzc.service.AdminPermissionService;
import com.xzc.utils.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 基于URL的过滤器
 * PathMatchingFilter是shiro提供的路径过滤器
 */
public class URLPathMatchingFilter extends PathMatchingFilter {

    @Autowired
    AdminPermissionService adminPermissionService;

    /**
     * 编写放行请求，判断是否具有权限：
     *  1. 判断当前会话对应的用户是否登录，如果未登录视为false
     *  2. 判断访问的*接口*是否有对应的权限，如果没有则视为不需要权限即可访问，直接true
     *  3. 如果需要权限，查询出当前用户对应的所有权限，遍历并与需要访问的接口进行对比，存在响应权限则true，否则为false
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    //protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    //    //放行option请求
    //    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    //    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    //    if (HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())){
    //        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
    //    }
    //
    //    /**在shiro的配置文件中，不能把URLFilter用@Bean管理
    //     * 因为这个也是过滤器，shiroFilterFactoryBean也是过滤器
    //     * 当他们都出现是，默认的anno,authc过滤器就消失了
    //     * 因此，无法使用@Autowired注入AdminPermissionService类
    //     * 需要借助工具类利用Spring获取实例*/
    //    if (null==adminPermissionService){
    //        adminPermissionService = SpringContextUtils.getContext().getBean(AdminPermissionService.class);
    //    }
    //
    //    String requestAPI = getPathWithinApplication(request);
    //    System.out.println("访问接口:"+requestAPI);
    //
    //    Subject subject = SecurityUtils.getSubject();
    //    if (!subject.isAuthenticated()) {
    //        System.out.println("需要登录");
    //        return false;
    //    }
    //
    //    boolean needFilter = adminPermissionService.needFilter(requestAPI);
    //    if(!needFilter){
    //        System.out.println("接口："+requestAPI+"无需权限");
    //        return true;
    //    }else {
    //        System.out.println("验证访问权限"+requestAPI);
    //        //判断当前用户是否有相应权限
    //        boolean hasPermission = false;
    //        String username = subject.getPrincipal().toString();
    //        Set<String> permissionAPIs = adminPermissionService.listPermissionURLsByUser(username);
    //        for (String api : permissionAPIs) {
    //            if (api.equals(requestAPI)){
    //                hasPermission =  true;
    //                break;
    //            }
    //        }
    //        if(hasPermission){
    //            System.out.println("访问权限:" + requestAPI + "验证成功");
    //            return true;
    //        }else {
    //            System.out.println("无访问权限:" + requestAPI + "验证失败");
    //            return false;
    //        }
    //    }
    //}

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //放行option请求
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())){
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        /**在shiro的配置文件中，不能把URLFilter用@Bean管理
         * 因为这个也是过滤器，shiroFilterFactoryBean也是过滤器
         * 当他们都出现是，默认的anno,authc过滤器就消失了
         * 因此，无法使用@Autowired注入AdminPermissionService类
         * 需要借助工具类利用Spring获取实例*/
        if (null==adminPermissionService){
            adminPermissionService = SpringContextUtils.getContext().getBean(AdminPermissionService.class);
        }

        String requestAPI = getPathWithinApplication(request);
        System.out.println("访问接口:"+requestAPI);

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            System.out.println("需要登录");
            return false;
        }

        boolean needFilter = adminPermissionService.needFilter(requestAPI);
        if(!needFilter){
            System.out.println("接口："+requestAPI+"无需权限");
            return true;
        }else {
            System.out.println("验证访问权限"+requestAPI);
            //判断当前用户是否有相应权限
            boolean hasPermission = false;
            String username = subject.getPrincipal().toString();
            Set<String> permissionAPIs = adminPermissionService.listPermissionURLsByUser(username);
            for (String api : permissionAPIs) {
                if (api.equals(requestAPI)){
                    hasPermission =  true;
                    break;
                }
            }
            if(hasPermission){
                System.out.println("访问权限:" + requestAPI + "验证成功");
                return true;
            }else {
                System.out.println("无访问权限:" + requestAPI + "验证失败");
                return false;
            }
        }
    }
}
