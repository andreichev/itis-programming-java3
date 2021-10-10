package ru.itis.servletsapp.servlets;

import ru.itis.servletsapp.dto.UserForm;
import ru.itis.servletsapp.services.SignUpService;
import ru.itis.servletsapp.services.validation.ErrorEntity;
import ru.itis.servletsapp.services.validation.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private SignUpService signUpService;
    private Validator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        this.signUpService = (SignUpService) context.getAttribute("signUpService");
        this.validator = (Validator) context.getAttribute("validator");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errors", new ArrayList<>());
        request.getRequestDispatcher("sign_up.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserForm form;
        try {
            form = UserForm.builder()
                    .email(request.getParameter("email"))
                    .firstName(request.getParameter("firstName"))
                    .lastName(request.getParameter("lastName"))
                    .password(request.getParameter("password"))
                    .age(Integer.parseInt(request.getParameter("age")))
                    .build();
        } catch (NumberFormatException e) {
            Set<ErrorEntity> errors = new HashSet<>();
            errors.add(ErrorEntity.INVALID_REQUEST);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("sign_up.ftl").forward(request, response);
            return;
        }
        Set<ErrorEntity> errors = validator.validate(form);
        if (errors.isEmpty() == false) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("sign_up.ftl").forward(request, response);
            return;
        }
        signUpService.signUp(form);
        response.sendRedirect("/signIn");
    }
}
