package com.itis.servletsexample4.services;

import com.itis.servletsexample4.model.FileInfo;

import java.io.InputStream;
import java.io.OutputStream;

public interface FilesService {
    FileInfo saveFileToStorage(InputStream file, String originalFileName, String contentType, Long size);
    void writeFileFromStorage(Long fileId, OutputStream outputStream);
    FileInfo getFileInfo(Long fileId);
}
