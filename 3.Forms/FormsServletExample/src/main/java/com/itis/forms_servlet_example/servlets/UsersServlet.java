package com.itis.forms_servlet_example.servlets;

import com.itis.forms_servlet_example.context.AppContext;
import com.itis.forms_servlet_example.dao.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("usersForJsp", AppContext.usersRepository.getAll());
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
