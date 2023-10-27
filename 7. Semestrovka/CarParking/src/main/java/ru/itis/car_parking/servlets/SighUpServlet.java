package ru.itis.car_parking.servlets;

import ru.itis.car_parking.dto.SignUpForm;
import ru.itis.car_parking.exceptions.ParkingException;
import ru.itis.car_parking.model.User;
import ru.itis.car_parking.services.AuthorizationService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@WebServlet("/sign-up")
public class SighUpServlet extends HttpServlet {
    private AuthorizationService authorizationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        authorizationService = (AuthorizationService) servletContext.getAttribute("authorizationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("sign-up.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate birthdate = LocalDate.parse(req.getParameter("birthdate"));
        SignUpForm form = SignUpForm.builder()
                .fistName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .birthdate(
                        birthdate.atStartOfDay(ZoneId.systemDefault()).toInstant()
                )
                .build();
        User user;
        try {
            user = authorizationService.signUp(form);
        } catch (ParkingException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("sign-up.ftl").forward(req, resp);
            return;
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);
        resp.sendRedirect("profile");
    }
}
