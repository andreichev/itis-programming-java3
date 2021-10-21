package ru.itis.servletsapp.servlets;

import ru.itis.servletsapp.exceptions.NotFoundException;
import ru.itis.servletsapp.model.FileInfo;
import ru.itis.servletsapp.services.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/files/*")
public class FilesDownloadServlet extends HttpServlet {
    private FilesService filesService;

    @Override
    public void init(ServletConfig config) {
        this.filesService = (FilesService) config.getServletContext().getAttribute("filesService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long fileId;
        try {
            String fileIdString = request.getRequestURI().substring(7);
            fileId = Long.parseLong(fileIdString);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.setStatus(400);
            response.getWriter().println("Wrong request");
            return;
        }

        try {
            FileInfo fileInfo = filesService.getFileInfo(fileId);
            response.setContentType(fileInfo.getType());
            response.setContentLength(fileInfo.getSize().intValue());
            response.setHeader("Content-Disposition", "filename=\"" + fileInfo.getOriginalFileName() + "\"");
            filesService.readFileFromStorage(fileId, response.getOutputStream());
            response.flushBuffer();
        } catch (NotFoundException e) {
            response.setStatus(404);
            response.getWriter().println(e.getMessage());
        }
    }
}
