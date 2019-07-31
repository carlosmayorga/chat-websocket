package com.backend.chat.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.chat.model.dao.ChatRepository;
import com.backend.chat.model.document.Mensaje;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatRepository chatDao;
    
    @Override
    public List<Mensaje> obtenerUltimos10Mensajes() {
        
        return chatDao.findFirst10ByOrderByFechaDesc();
    }

    @Override
    public Mensaje guardar(Mensaje mensaje) {
        
        return chatDao.save(mensaje);
    }

}
