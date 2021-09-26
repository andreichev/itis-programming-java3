package com.itis.servletsexample.cookiesexample;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebServlet("/read-cookie")
public class ReadCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<Cookie> optionalCookie = Arrays.stream(
                request.getCookies())
                .filter(item -> item.getName().equals("Message"))
                .findFirst();
        String message = optionalCookie.isPresent() ?
                optionalCookie.get().getValue() : "No cookie";

        System.out.println(message);
        response.getWriter().println(message);
    }
}
