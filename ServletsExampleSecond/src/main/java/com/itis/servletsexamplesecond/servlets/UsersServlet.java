package com.itis.servletsexamplesecond.servlets;

import com.itis.servletsexamplesecond.model.User;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .id(0)
                .firstName("Ivan")
                .lastName("Ivanov")
                .age(20)
                .build());
        users.add(User.builder()
                .id(1)
                .firstName("Anton")
                .lastName("Kulikov")
                .age(20)
                .build());

        request.setAttribute("usersForJsp", users);
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
