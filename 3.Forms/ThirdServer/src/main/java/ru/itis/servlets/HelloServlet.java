package ru.itis.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    int x = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("SERVLET START TEST TEST");
        PrintWriter writer = response.getWriter();
        writer.println("<h1>HELLO, WORLD!" + x + "</h1>");
        response.setContentType("text/html");
        x++;
    }
}
