package ru.itis.servletsapp.servlets;

import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.dto.UserForm;
import ru.itis.servletsapp.exceptions.ValidationException;
import ru.itis.servletsapp.services.SignInService;

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
    private SignInService signInService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        this.signInService = (SignInService) context.getAttribute("signInService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("sign_in.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        UserForm form = new UserForm(
//                request.getParameter("email"),
//                null, null,
//                request.getParameter("password"),
//                null
//        );
        UserForm form = UserForm.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();
        UserDto userDto;

        try {
            userDto = signInService.signIn(form);
        } catch (ValidationException e) {
            response.sendRedirect("/sign-in");
            return;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("user", userDto);
        response.sendRedirect("/profile");
    }
}
