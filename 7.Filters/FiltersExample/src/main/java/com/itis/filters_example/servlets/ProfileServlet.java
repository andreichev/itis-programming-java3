package com.itis.filters_example.servlets;

import com.itis.filters_example.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDto profile = (UserDto) session.getAttribute("profile");
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("/profile.ftl").forward(request, response);
    }
}
