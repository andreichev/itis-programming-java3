package ru.itis.sessionsexample.servlet;

import ru.itis.sessionsexample.service.SessionsManager;
import ru.itis.sessionsexample.service.model.Session;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-out")
public class SignOutServlet extends HttpServlet {
    private SessionsManager sessionsManager;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        sessionsManager = (SessionsManager) context.getAttribute("sessionsManager");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = sessionsManager.getSession(false, request, response);
        if(session != null) {
            session.removeAttribute("user");
        }
        response.sendRedirect("/sign-in");
    }
}
