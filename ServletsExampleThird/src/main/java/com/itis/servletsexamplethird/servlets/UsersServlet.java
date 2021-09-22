package com.itis.servletsexamplethird.servlets;

import com.itis.servletsexamplethird.model.User;
import com.itis.servletsexamplethird.repository.UsersRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        usersRepository = (UsersRepository) servletContext.getAttribute("usersRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = usersRepository.findAll();
        request.setAttribute("usersForJsp", users);
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
