package com.itis.servletsexamplethird.servlets;

import com.itis.servletsexamplethird.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        System.out.println(servletContext.getAttribute("Message"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .id(0L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .age(20)
                .build());
        users.add(User.builder()
                .id(1L)
                .firstName("Anton")
                .lastName("Kulikov")
                .age(20)
                .build());

        request.setAttribute("usersForJsp", users);
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
