package com.itis.filters_example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itis.filters_example.dto.RegistrationForm;
import com.itis.filters_example.dto.UserDto;
import com.itis.filters_example.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    UsersService usersService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        usersService = (UsersService) context.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/registration.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationForm registrationForm = objectMapper.readValue(request.getReader(), RegistrationForm.class);
        UserDto profile = usersService.register(registrationForm);
        HttpSession session = request.getSession(true);
        session.setAttribute("profile", profile);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), profile);
    }
}
