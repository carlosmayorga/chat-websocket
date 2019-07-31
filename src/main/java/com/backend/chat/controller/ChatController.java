package com.backend.chat.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.backend.chat.model.document.Mensaje;
import com.backend.chat.model.service.ChatService;

@Controller
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    
    @Autowired
    private SimpMessagingTemplate webSocket;

    private String [] colores = {"red", "green", "blue", "purple"};
    
    @MessageMapping("/mensaje")
    @SendTo("/chat/mensaje")
    public Mensaje recibirMensaje(Mensaje mensaje) {
        mensaje.setFecha(new Date().getTime());
        
        if (mensaje.getTipo().equals("NUEVO_USUARIO")) {
            mensaje.setColor(colores[new Random().nextInt(colores.length)]);
            mensaje.setTexto("Nuevo usuario");   
        } else {
            chatService.guardar(mensaje);
        }
            
        return mensaje;
    }
    
    @MessageMapping("/escribiendo")
    @SendTo("/chat/escribiendo")
    public String escribiendo(String username) {
        return username.concat(" está escribiendo...");
    }
    
    @MessageMapping("/historial")
    public void historial(String clienteId){
        webSocket.convertAndSend("/chat/historial/" + clienteId, chatService.obtenerUltimos10Mensajes());
    }
}
