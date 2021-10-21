package ru.itis.servletsapp.servlets;

import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.model.FileInfo;
import ru.itis.servletsapp.services.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/update-avatar")
@MultipartConfig
public class UpdateAvatarServlet extends HttpServlet {
    private FilesService filesService;

    @Override
    public void init(ServletConfig config) {
        this.filesService = (FilesService) config.getServletContext().getAttribute("filesService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");
        HttpSession session = request.getSession(true);
        UserDto userDto = (UserDto) session.getAttribute("user");
        FileInfo fileInfo = filesService.saveFileToStorage(
                userDto,
                part.getInputStream(),
                part.getSubmittedFileName(),
                part.getContentType(),
                part.getSize());
        userDto.setAvatarId(fileInfo.getId());
        session.setAttribute("user", userDto);
        response.sendRedirect("/files/" + fileInfo.getId());
    }
}
