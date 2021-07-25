package com.github.patu11.mafiaroller.user;

import com.github.patu11.mafiaroller.dto.UserDTO;
import com.github.patu11.mafiaroller.room.RoomData;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<UserDTO> users = this.userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/free/{username}")
	public ResponseEntity<?> isUsernameTaken(@PathVariable String username) {
		return ResponseEntity.ok(this.userService.isUsernameTaken(username));

	}

	@GetMapping("/{roomCode}")
	public ResponseEntity<?> getAllByRoomCode(@PathVariable String roomCode) {
		List<UserDTO> users = this.userService.getAllUsersByRoomCode(roomCode);
		return ResponseEntity.ok(users);
	}

	@PostMapping()
	public ResponseEntity<?> addUser(@RequestBody UserData userData) {
		this.userService.addUser(userData);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
