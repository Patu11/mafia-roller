package com.github.patu11.mafiaroller.user;

import com.github.patu11.mafiaroller.NotFoundException;
import com.github.patu11.mafiaroller.dto.UserDTO;
import com.github.patu11.mafiaroller.room.Room;
import com.github.patu11.mafiaroller.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		Optional<User> user = this.userRepository.findById(username);
		if (user.isPresent()) return user.get();
		else throw new NotFoundException("User not found");
	}

	@Transactional(readOnly = true)
	public List<User> getAllRawUsersFromRoom(String roomCode) {
		return this.userRepository.findAllByRoomCode(roomCode);
	}

	@Transactional(readOnly = true)
	public User getRawUserByUsername(String username) {
		return this.userRepository.findById(username).orElseThrow(() -> new NotFoundException("Raw user not found"));
	}

	@Transactional
	public void updateUser(User user) {
		this.userRepository.save(user);
	}

	@Transactional
	public void updateAll(List<User> users) {
		this.userRepository.saveAll(users);
	}

	@Transactional
	public void addUser(UserData user) {
		User u = new User();
		u.setUsername(user.getUsername());
		u.setRole(user.getRole());
		this.userRepository.save(u);
	}

	@Transactional(readOnly = true)
	public boolean isUsernameTaken(String username) {
		return this.userRepository.existsById(username);
	}

	@Transactional(readOnly = true)
	public List<UserDTO> getAllUsers() {
		return this.userRepository.findAll().stream()
				.map(user -> new UserDTO(user.getUsername(), (user.getRoom() == null) ? "" : user.getRoom().getCode(), user.getRole(), user.isDead(), user.isStarted()))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<UserDTO> getAllUsersByRoomCode(String roomCode) {

		return this.userRepository.findAllByRoomCodeOrderByUsername(roomCode).stream()
				.map(user -> new UserDTO(user.getUsername(), user.getRoom().getCode(), user.getRole(), user.isDead(), user.isStarted()))
				.collect(Collectors.toList());
//		return this.userRepository.findAllByRoomCode(roomCode).stream()
//				.map(user -> new UserDTO(user.getUsername(), user.getRoom().getCode(), user.getRole(), user.isDead(), user.isStarted()))
//				.collect(Collectors.toList());
	}

	@Transactional
	public void deleteAllUsers() {
		this.userRepository.deleteAll();
	}

}
