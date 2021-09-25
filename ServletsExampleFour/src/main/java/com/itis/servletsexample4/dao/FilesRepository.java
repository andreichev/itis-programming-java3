package com.itis.servletsexample4.dao;

import com.itis.servletsexample4.dao.base.CrudRepository;
import com.itis.servletsexample4.model.FileInfo;

public interface FilesRepository extends CrudRepository<FileInfo, Long> {}
