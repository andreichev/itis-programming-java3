package com.itis.servletsexample4.services.impl;

import com.itis.servletsexample4.dao.FilesRepository;
import com.itis.servletsexample4.exceptions.FileSizeException;
import com.itis.servletsexample4.exceptions.NotFoundException;
import com.itis.servletsexample4.model.FileInfo;
import com.itis.servletsexample4.services.FilesService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class FilesServiceImpl implements FilesService {

    String path = "/Users/andreichev/Documents/server_files/";

    private final FilesRepository filesRepository;

    public FilesServiceImpl(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    @Override
    public FileInfo saveFileToStorage(InputStream inputStream, String originalFileName, String contentType, Long size) {
        if(size > 10_000_000) {
            throw new FileSizeException("File is too large");
        }
        FileInfo fileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );
        try {
            Files.copy(inputStream, Paths.get(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            fileInfo = filesRepository.save(fileInfo);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return fileInfo;
    }

    @Override
    public void readFileFromStorage(Long fileId, OutputStream outputStream) {
        FileInfo fileInfo = filesRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File not found"));

        File file = new File(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public FileInfo getFileInfo(Long fileId) {
        return filesRepository.findById(fileId).orElseThrow(() -> new NotFoundException("File not found"));
    }

    @Override
    public void delete(Long fileId) {
        // TODO: - Реализовать
    }
}
