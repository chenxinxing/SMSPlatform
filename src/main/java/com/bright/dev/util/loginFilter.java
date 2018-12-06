package com.bright.dev.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: Bright
 * @Date: 2018/12/5 15:34
 */
public class loginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        //String flag = (String) session.getAttribute("loginFlag");
        if (session.getAttribute("loginFlag") == null ) {
            if (request.getRequestURI().contains("/index.html") || request.getRequestURI().contains("login")) {
                chain.doFilter(req, resp);
                return;
            }else{
                //request.getRequestDispatcher(request.getContextPath()).forward(request, response);
                response.sendRedirect("/SMSPlatform/index.html");
                return;
            }
        } else
            chain.doFilter(req, resp);
        //chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
