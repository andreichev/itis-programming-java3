package ru.itis.sessionsexample.listenters;

import ru.itis.sessionsexample.repository.UserRepository;
import ru.itis.sessionsexample.service.SessionsManager;
import ru.itis.sessionsexample.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        SessionsManager sessionsManager = new SessionsManager();

        context.setAttribute("userService", userService);
        context.setAttribute("sessionsManager", sessionsManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
