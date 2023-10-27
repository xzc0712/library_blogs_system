package com.xzc.interceptor;


import com.xzc.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.apache.commons.lang.StringUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginInterceptor implements HandlerInterceptor {
    //分别代表HTTP请求、响应以及被拦截的处理器(Handler)对象
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //*******使用shiro中自带session*********
        // 放行option请求，否则无法让前端带上自定义的header信息，导致sessionID改变，验证失败
        if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        //使用shiro验证
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()&&!subject.isRemembered()){
            return false;
        }else {
            return true;
        }

        //*******************原始使用session进行拦截*****************

        ////设置需要拦截的路径
        //String[] requireAuthPages = new String[]{
        //        "index",
        //        "123"
        //};
        //
        ////获取到session内容
        //HttpSession session = request.getSession();
        ////session.getServletContext()获取应用程序的上下文对象
        ////getContextPath()方法用于获取应用程序的上下文路径
        //String contextPath = session.getServletContext().getContextPath();
        //
        //String uri = request.getRequestURI();
        ////http://localhost:8443/index/xzc/2返回 indexxxzc2
        //uri = StringUtils.remove(uri, contextPath + "/");
        //
        //String page = uri;
        //// 首先得满足以配置的路径开头
        //if (beginWith(page,requireAuthPages)){
        //    User user = (User) session.getAttribute("user");
        //    if (user==null){
        //        //不存在session中的user
        //        response.sendRedirect("login");
        //        return false;
        //    }
        //}
        //return true;
    }


    //该方法用于确定 URL 是否以配置的拦截路径开头。如果 URL 以 requireAuthPages中配置的某一项开头，则返回true
    private boolean beginWith(String page , String[]requireAuthPages){
        boolean res = false;
        for (String requireAuthPage : requireAuthPages) {
            if (requireAuthPage.startsWith(page)){
                res = true;
                break;
            }
        }
        return res;
    }
}
