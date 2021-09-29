package com.itis.servletsexample.sessionexample;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (request.getSession(true).getAttribute("User") != null) {
            String username = (String) request.getSession().getAttribute("User");
            System.out.println("USER: " + username);
            request.setAttribute("userName", username);
            request.getRequestDispatcher("profile.ftl").forward(request, response);
        } else {
            response.sendRedirect("/sign-in");
        }
    }
}
