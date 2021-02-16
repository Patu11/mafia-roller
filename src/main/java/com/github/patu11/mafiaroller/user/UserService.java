package com.github.patu11.mafiaroller.user;

import com.github.patu11.mafiaroller.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return this.userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getUsername()))
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllUsersByRoomCode(String roomCode) {
        return this.userRepository.findAllByRoomCode(roomCode).stream()
                .map(user -> new UserDTO(user.getUsername()))
                .collect(Collectors.toList());
    }

}
