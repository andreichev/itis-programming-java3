package ru.itis.servletsapp.dao;

import ru.itis.servletsapp.dao.base.CrudRepository;
import ru.itis.servletsapp.model.FileInfo;

public interface FilesRepository extends CrudRepository<FileInfo, Long> {}
