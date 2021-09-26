package com.itis.servletsexample.cookiesexample;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/add-cookie")
public class AddCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Сообщение, которое сохранится на стороне клиента в куке с именем Message на 10 секунд.
        String messageToSaveAtClient = UUID.randomUUID().toString();

        Cookie cookie = new Cookie("Message", messageToSaveAtClient);
        cookie.setMaxAge(10);

        response.addCookie(cookie);
        response.getWriter().println(messageToSaveAtClient);
    }
}
