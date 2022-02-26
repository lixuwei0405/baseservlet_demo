package com.lixw.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2022/2/23 15:20
 *
 * @author lixw
 * @version 1.0
 */
public class UserServlet {
    public String regist(HttpServletRequest req, HttpServletResponse resp){
        System.err.println("UserServlet中的方法regist执行了");
        return ServletResult.REDIRECT+"/index.jsp";
    }

    public String login(HttpServletRequest req, HttpServletResponse resp){
        System.err.println("UserServlet中的方法login执行了");
        return ServletResult.FORWARD+"/2222.jsp";
    }
}
