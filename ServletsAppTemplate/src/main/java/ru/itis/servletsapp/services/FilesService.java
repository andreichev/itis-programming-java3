package ru.itis.servletsapp.services;

import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.model.FileInfo;

import java.io.InputStream;
import java.io.OutputStream;

public interface FilesService {
    FileInfo saveFileToStorage(UserDto user, InputStream file, String originalFileName, String contentType, Long size);
    void readFileFromStorage(Long fileId, OutputStream outputStream);
    FileInfo getFileInfo(Long fileId);
}
