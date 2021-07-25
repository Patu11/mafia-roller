package com.github.patu11.mafiaroller.websocket;

import com.github.patu11.mafiaroller.NotFoundException;
import com.github.patu11.mafiaroller.RoleGenerator;
import com.github.patu11.mafiaroller.dto.RoomDTO;
import com.github.patu11.mafiaroller.dto.UserDTO;
import com.github.patu11.mafiaroller.room.Room;
import com.github.patu11.mafiaroller.room.RoomData;
import com.github.patu11.mafiaroller.room.RoomService;
import com.github.patu11.mafiaroller.user.User;
import com.github.patu11.mafiaroller.user.UserData;
import com.github.patu11.mafiaroller.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebsocketService {

	private final UserService userService;
	private final RoomService roomService;

	@Autowired
	public WebsocketService(UserService userService, RoomService roomService) {
		this.userService = userService;
		this.roomService = roomService;
	}

	public List<UserDTO> sendUsersList(String roomCode) {
		return this.userService.getAllUsersByRoomCode(roomCode);
	}

	public List<UserDTO> changeDead(UserData userData) {
		User user = this.userService.getRawUserByUsername(userData.getUsername());
		user.setDead(userData.isDead());
		this.userService.updateUser(user);
		return this.userService.getAllUsersByRoomCode(userData.getRoomCode());
	}

	public List<UserDTO> join(UserData userData) {
		this.roomService.joinUserToRoom(userData);
		return this.userService.getAllUsersByRoomCode(userData.getRoomCode());

	}

	public List<UserDTO> leave(UserData userData) {
		this.roomService.removeUserFromRoom(userData);
		return this.userService.getAllUsersByRoomCode(userData.getRoomCode());
	}

	public List<UserDTO> startGame(UserData userData) {
		List<UserDTO> users = this.userService.getAllUsersByRoomCode(userData.getRoomCode());
		Room r = this.roomService.getRawRoomByCode(users.get(0).getRoomCode());

		RoleGenerator generator = new RoleGenerator(users);
		List<UserDTO> generated = generator.getGeneratedUsersRoles();

		List<User> toUpdate = new ArrayList<>();

		for (UserDTO u : generated) {
			toUpdate.add(new User(u.getUsername(), r, u.getRole(), u.isDead()));
		}

		this.userService.updateAll(toUpdate);

		return generated;
	}
}
