package com.github.patu11.mafiaroller.websocket;

import com.github.patu11.mafiaroller.dto.RoomDTO;
import com.github.patu11.mafiaroller.dto.UserDTO;
import com.github.patu11.mafiaroller.room.RoomData;
import com.github.patu11.mafiaroller.room.RoomService;
import com.github.patu11.mafiaroller.user.User;
import com.github.patu11.mafiaroller.user.UserData;
import com.github.patu11.mafiaroller.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
public class WebsocketController {

	private final WebsocketService websocketService;

	@Autowired
	public WebsocketController(WebsocketService websocketService) {
		this.websocketService = websocketService;
	}

	@MessageMapping("/send/{roomCode}")    //to send data
	@SendTo("/game/{roomCode}")    //to subscribe
	public List<UserDTO> handleRoom(@DestinationVariable String roomCode) {
		return this.websocketService.sendUsersList(roomCode);
	}

	@MessageMapping("/send/new/{roomCode}")
	@SendTo("/game/{roomCode}")
	public List<UserDTO> newGame(@DestinationVariable String roomCode) {
		return this.websocketService.newGame(roomCode);
	}

	@MessageMapping("/send/host/{roomCode}")
	@SendTo("/game/{roomCode}")
	public List<UserDTO> makeHost(@DestinationVariable String roomCode, HostData hostData) {
		return this.websocketService.makeHost(hostData, roomCode);
	}

	@MessageMapping("/send/dead/{roomCode}")
	@SendTo("/game/{roomCode}")
	public List<UserDTO> changeDead(@DestinationVariable String roomCode, UserData user) {
		return this.websocketService.changeDead(user);
	}

	@MessageMapping("/send/join/{roomCode}")    //to send data
	@SendTo("/game/{roomCode}")    //to subscribe
	public List<UserDTO> joinRoom(@DestinationVariable String roomCode, UserData user) {
		return this.websocketService.join(user);
	}

	@MessageMapping("/send/leave/{roomCode}")    //to send data
	@SendTo("/game/{roomCode}")    //to subscribe
	public List<UserDTO> leaveRoom(@DestinationVariable String roomCode, UserData user) {
		return this.websocketService.leave(user);
	}

	@MessageMapping("/send/start/{roomCode}/{freak}")    //to send data
	@SendTo("/game/{roomCode}")    //to subscribe
	public List<UserDTO> startGame(@DestinationVariable String roomCode, @DestinationVariable boolean freak, UserData user) {
		System.out.println(freak);
		return this.websocketService.startGame(user, freak);
	}
}
