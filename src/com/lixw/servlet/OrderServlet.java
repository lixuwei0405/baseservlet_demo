package com.lixw.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2022/2/23 15:02
 *
 * @author lixw
 * @version 1.0
 */
public class OrderServlet {
    public String add(HttpServletRequest req, HttpServletResponse resp){
        System.err.println("OderServlet中的方法add执行了");
        return ServletResult.REDIRECT+"/index.jsp";
    }
    public String update(HttpServletRequest req, HttpServletResponse resp){
        System.err.println("OderServlet中的方法update执行了");
        return "祝你每天都开开心心";
    }
}
