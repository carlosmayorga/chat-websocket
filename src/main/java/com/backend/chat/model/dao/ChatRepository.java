package com.backend.chat.model.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.chat.model.document.Mensaje;

public interface ChatRepository extends MongoRepository<Mensaje, String> {

    public List<Mensaje> findFirst10ByOrderByFechaDesc();
}
