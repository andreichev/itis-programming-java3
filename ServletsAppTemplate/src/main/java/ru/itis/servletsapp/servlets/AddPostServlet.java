package ru.itis.servletsapp.servlets;

import ru.itis.servletsapp.dto.out.PostDto;
import ru.itis.servletsapp.dto.out.UserDto;
import ru.itis.servletsapp.services.PostsService;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@WebServlet("/add-post")
public class AddPostServlet extends HttpServlet {
    private PostsService postsService;

    @Override
    public void init(ServletConfig config) {
        postsService = (PostsService) config.getServletContext().getAttribute("postsService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
        PostDto form = PostDto.builder()
                .content(request.getParameter("content"))
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .author(userDto)
                .build();
        postsService.addPost(form);
        response.sendRedirect("/profile");
    }
}
