package ru.itis.sessionsexample.servlet;

import ru.itis.sessionsexample.model.User;
import ru.itis.sessionsexample.service.SessionsManager;
import ru.itis.sessionsexample.service.UserService;
import ru.itis.sessionsexample.service.model.Session;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {
    private UserService userService;
    private SessionsManager sessionsManager;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        sessionsManager = (SessionsManager) context.getAttribute("sessionsManager");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = sessionsManager.getSession(false, request, response);
        if(session != null && session.getAttribute("user") != null) {
            response.sendRedirect("/profile");
            return;
        }
        request.getRequestDispatcher("/view/signIn.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user;
        try {
            user = userService.signIn(name, password);
        } catch (Exception e) {
            response.sendRedirect("/sign-in");
            return;
        }
        Session session = sessionsManager.getSession(true, request, response);
        session.setAttribute("user", user);

        response.sendRedirect("/profile");
    }
}
