package com.itis.forms_servlet_example.servlets;

import com.itis.forms_servlet_example.dao.UsersRepository;
import com.itis.forms_servlet_example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/add")
public class AddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/add-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = User.builder()
                .id(UsersRepository.shared.users.size())
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .courseName(request.getParameter("courseName"))
                .age(Integer.valueOf(request.getParameter("age")))
                .build();
        UsersRepository.shared.users.add(user);
        response.sendRedirect("/users");
    }
}
