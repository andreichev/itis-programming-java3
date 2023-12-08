package com.itis.forms_servlet_example.servlets;

import com.itis.forms_servlet_example.dao.UsersRepository;
import com.itis.forms_servlet_example.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/add")
public class AddUser extends HttpServlet {
    UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        usersRepository = (UsersRepository) context.getAttribute("usersRepository");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String line;
//        while((line = request.getReader().readLine()) != null) {
//            System.out.println(line);
//        }
        User user = User.builder()
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .courseName(request.getParameter("courseName"))
                .age(Integer.valueOf(request.getParameter("age")))
                .build();
        usersRepository.save(user);
        response.sendRedirect("/users");
    }
}
