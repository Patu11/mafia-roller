package com.github.patu11.mafiaroller.room;

import com.github.patu11.mafiaroller.dto.RoomDTO;
import com.github.patu11.mafiaroller.user.UserData;
import com.github.patu11.mafiaroller.websocket.RoomUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://mafia-roller.herokuapp.com")
@RequestMapping("/rooms")
public class RoomController {

	private final RoomService roomService;

	@Autowired
	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<RoomDTO> rooms = this.roomService.getAllRooms();
		return ResponseEntity.ok(rooms);
	}

	@GetMapping("/{code}")
	public ResponseEntity<?> getRoom(@PathVariable String code) {
		RoomDTO room = this.roomService.getRoomByCode(code);
		return ResponseEntity.ok(room);
	}

	@PostMapping
	public ResponseEntity<?> createRoom(@RequestBody RoomUserData roomUserData) {
		this.roomService.addRoomWithCode(roomUserData);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/join")
	public ResponseEntity<?> joinRoom(@RequestBody UserData joinLeaveData) {
		this.roomService.joinUserToRoom(joinLeaveData);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/leave")
	public ResponseEntity<?> leaveRoom(@RequestBody UserData joinLeaveData) {
		this.roomService.removeUserFromRoom(joinLeaveData);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
