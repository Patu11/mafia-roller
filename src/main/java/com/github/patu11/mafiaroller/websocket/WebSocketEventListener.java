package com.github.patu11.mafiaroller.websocket;

import com.github.patu11.mafiaroller.dto.UserDTO;
import com.github.patu11.mafiaroller.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.messaging.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketEventListener {

	private final SimpMessagingTemplate template;
	private final UserService userService;

	@Autowired
	public WebSocketEventListener(SimpMessagingTemplate template, UserService userService) {
		this.template = template;
		this.userService = userService;
	}

	@EventListener
	public void handleSessionConnected(SessionConnectEvent event) {
		String roomCode = (String) event.getMessage().getHeaders().getOrDefault("code", "NONE");
//		System.out.println(roomCode);
		List<UserDTO> users = this.userService.getAllUsersByRoomCode(roomCode);
//		this.template.convertAndSend("/game/" + roomCode, users);
//		System.out.println("CONNECTED" + event.getMessage());
//		System.out.println("Connected " + event.getMessage());
	}

	@EventListener
	public void handleSessionDisconnected(SessionDisconnectEvent event) {
		String roomCode = (String) event.getMessage().getHeaders().getOrDefault("roomCode", "NONE");
//		System.out.println(roomCode);
		List<UserDTO> users = this.userService.getAllUsersByRoomCode(roomCode);
//		this.template.convertAndSend("/game/" + roomCode, users);
//		System.out.println("DISCONNECTED" + event.getMessage());
//		System.out.println("Disconnected " + event.getMessage());
	}

	@EventListener
	public void handleSessionSubscribed(SessionSubscribeEvent event) {
//		System.out.println("SUBSCRIBED" + event.getMessage());
	}

	@EventListener
	public void handleSessionUnsubscribed(SessionUnsubscribeEvent event) {
//		System.out.println("UNSUBSCRIBED" + event.getMessage());
	}
}
