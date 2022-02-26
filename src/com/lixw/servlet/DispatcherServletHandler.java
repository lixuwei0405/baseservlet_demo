package com.lixw.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2022/2/23 14:34
 *
 * @author lixw
 * @version 1.0
 */
@WebServlet(value = "/",loadOnStartup = 0)
public class DispatcherServletHandler extends HttpServlet {
    //创建Servlet和Method映射集
    private Map<String,Object> servletMapping = new ConcurrentHashMap<>();
    private Map<String, Method> methodMapping = new ConcurrentHashMap<>();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求地址
        String requestURI = req.getRequestURI();
        if("/favicon.ico".equals(requestURI)){
            return;
        }
        //2.获取要执行的方法
        String method = req.getParameter("method");
        //如果访问的是项目根路径 暂不处理
        if("/".equals(requestURI)){

        }else{
            try {
                //从映射集中取出Servlet实例和方法映射集
                Object target = servletMapping.get(requestURI);
                Method targetMethod = methodMapping.get(method);
                //如果取出的实例为空则进行创建
                if(target==null){
                    //找到地址对应的类,首字母大写+Servlet
                    String path = requestURI.substring(1);// base
                    path = path.replace(path.charAt(0), path.toUpperCase().charAt(0)); //Base
                    String className = path+"Servlet";// BaseServlet
                    //获取当前类的包名
                    String packageName = this.getClass().getPackage().getName();//com.lixw.servlet
                    //拼接全路径名
                    String fullClassName = packageName+"."+className; //com.lixw.servlet.BaseServlet
                    //获取Servlet类对象
                    Class<?> clazz = Class.forName(fullClassName);
                    //创建该Servlet实例
                    target = clazz.newInstance();
                    //将该target存入Servlet映射集中  key:requestURI value:instance
                    servletMapping.put(requestURI, target);
                }
                //如果取出的方法对象不存在则动态获取
                if(targetMethod==null){
                    if(method==null || method.trim().equals("")){
                        //没有传递method就去执行默认的方法
                        method = "defaultMethod";
                    }
                    //从目标类中找到目标方法
                    try {
                        targetMethod= target.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
                    } catch (Exception e) {
                        System.out.println("没有找到这个方法！");
                        e.printStackTrace();
                    }
                    //将方法对象放入到方法映射集中
                    methodMapping.put(method, targetMethod);
                }
                Object result = targetMethod.invoke(target, req, resp);
                //返回类型必须是字符串
                if(result!=null && result instanceof String){
                    String resultData = (String) result;
                    if(resultData.startsWith(ServletResult.REDIRECT)){
                        //重定向
                        resp.sendRedirect(resultData.replaceAll(ServletResult.REDIRECT, ""));
                    }else if(resultData.startsWith(ServletResult.FORWARD)){
                        //转发操作
                        req.getRequestDispatcher(resultData.replaceAll(ServletResult.FORWARD, "")).forward(req, resp);
                    }else{
                        //如果前缀没有任何内容则代表返回数据到前台
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().write(resultData);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
