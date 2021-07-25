package com.github.patu11.mafiaroller.room;

import com.github.patu11.mafiaroller.CodeGenerator;
import com.github.patu11.mafiaroller.MafiaRoles;
import com.github.patu11.mafiaroller.NotFoundException;
import com.github.patu11.mafiaroller.dto.RoomDTO;
import com.github.patu11.mafiaroller.dto.UserDTO;
import com.github.patu11.mafiaroller.user.User;
import com.github.patu11.mafiaroller.user.UserData;
import com.github.patu11.mafiaroller.user.UserService;
import com.github.patu11.mafiaroller.websocket.RoomUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class RoomService {

	private final RoomRepository roomRepository;
	private UserService userService;

	@Autowired
	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Transactional(readOnly = true)
	public List<RoomDTO> getAllRooms() {
		List<RoomDTO> list = this.roomRepository.findAll().stream()
				.map(room -> new RoomDTO(room.getCode(), room.getName(), this.userService.getAllUsersByRoomCode(room.getCode())))
				.collect(Collectors.toList());

		if (list.isEmpty()) {
//			throw new NotFoundException("No rooms found.");
			return new ArrayList<>();
		}
		return list;
	}

	@Transactional
	public void removeUserFromRoom(UserData joinLeaveData) {
		User user = this.userService.getUserByUsername(joinLeaveData.getUsername());
		Optional<Room> r = this.roomRepository.findById(joinLeaveData.getRoomCode());

		if (r.isPresent()) {
			Room room = r.get();
			room.deleteUser(user);
			this.roomRepository.save(room);
			this.userService.updateUser(user);
		} else {
			throw new NotFoundException("Room not found, cannot leave");
		}
	}


	@Transactional
	public void joinUserToRoom(UserData joinLeaveData) {
		User user = this.userService.getUserByUsername(joinLeaveData.getUsername());
		Optional<Room> r = this.roomRepository.findById(joinLeaveData.getRoomCode());

		if (r.isPresent()) {
			Room room = r.get();
			room.addUser(user);
			if (joinLeaveData.getRole().equals(MafiaRoles.HOST.name())) {
				user.setRole(MafiaRoles.HOST.name());
			}

			this.roomRepository.save(room);    //added
			this.userService.updateUser(user);    //added
		} else {
			throw new NotFoundException("Room not found, cannot join");
		}
	}

//	@Transactional
//	public void addRoomWithCode(RoomUserData roomUserData) {
//		Room newRoom = new Room();
//		newRoom.setName(roomUserData.getName());
//		newRoom.setCode(roomUserData.getCode());
//
//		User user = this.userService.getUserByUsername(roomUserData.getUsername());
//
//		newRoom.addUser(user);
//
//		this.roomRepository.save(newRoom);
//
////		UserData userData = new UserData();
////		userData.setUsername(roomUserData.getUsername());
////		userData.setRoomCode(newRoom.getCode());
////		this.joinUserToRoom(userData);
//	}

	@Transactional
	public void addRoomWithCode(RoomUserData roomUserData) {
		Room newRoom = new Room();
		newRoom.setName(roomUserData.getName());
		newRoom.setCode(roomUserData.getCode());
		this.roomRepository.save(newRoom);

		UserData userData = new UserData();
		userData.setUsername(roomUserData.getUsername());
		userData.setRoomCode(newRoom.getCode());
		userData.setRole("HOST");
		this.joinUserToRoom(userData);
	}

//	@Transactional(readOnly = true)
//	public Future<Room> getRoom(String code) {
//		Optional<Room> room = this.roomRepository.findById(code);
//		if (room.isPresent()) return new AsyncResult<>(room.get());
//		else throw new NotFoundException("Room not found");
//	}

	@Transactional(readOnly = true)
	public RoomDTO getRoomByCode(String code) {
		Optional<Room> optional = this.roomRepository.findById(code);
		if (optional.isEmpty()) {
			throw new NotFoundException("Room not found.");
		}

		List<UserDTO> users = this.userService.getAllUsersByRoomCode(code);

		return new RoomDTO(optional.get().getCode(), optional.get().getName(), users);
	}


	@Transactional
	public void addRoom(RoomData roomData) {
		if (roomData.getName().isEmpty() || roomData.getName().isBlank()) {
			throw new IllegalArgumentException("Room name cannot be empty.");
		}

		Room newRoom = new Room();
		String code = CodeGenerator.generate();

		while (!isAvailable(code)) {
			code = CodeGenerator.generate();
		}

		newRoom.setName(roomData.getName());
		newRoom.setCode(code);
		this.roomRepository.save(newRoom);
	}

	@Transactional(readOnly = true)
	public Room getRawRoomByCode(String code) {
		return this.roomRepository.findById(code).orElseThrow(() -> new NotFoundException("Raw room not found"));
	}


	@Transactional
	public void deleteEmptyRooms() {
		List<Room> rooms = this.roomRepository.findAll();
		for (Room r : rooms) {
			if (r.getUsers().isEmpty()) {
				this.roomRepository.deleteById(r.getCode());
			}
		}
	}


	private boolean isAvailable(String code) {
		Optional<String> dbCode = this.roomRepository.findCode(code);
		return dbCode.isEmpty();
	}
}
