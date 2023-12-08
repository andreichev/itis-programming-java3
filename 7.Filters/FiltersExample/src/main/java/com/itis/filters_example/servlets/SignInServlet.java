package com.itis.filters_example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itis.filters_example.dto.ErrorDto;
import com.itis.filters_example.dto.SignInForm;
import com.itis.filters_example.dto.UserDto;
import com.itis.filters_example.excepcions.IncorrectPasswordException;
import com.itis.filters_example.excepcions.NotFoundException;
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

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {
    private UsersService usersService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        usersService = (UsersService) context.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/signIn.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SignInForm form = objectMapper.readValue(request.getReader(), SignInForm.class);
        response.setContentType("application/json");
        UserDto user;
        try {
            user = usersService.signIn(form);
        } catch (NotFoundException e) {
            response.setStatus(404);
            System.out.println("404 " + e.getMessage());
            objectMapper.writeValue(response.getWriter(), new ErrorDto(e.getMessage()));
            return;
        } catch (IncorrectPasswordException e) {
            response.setStatus(400);
            System.out.println("400 " + e.getMessage());
            objectMapper.writeValue(response.getWriter(), new ErrorDto(e.getMessage()));
            return;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("profile", user);
        objectMapper.writeValue(response.getWriter(), user);
    }
}
