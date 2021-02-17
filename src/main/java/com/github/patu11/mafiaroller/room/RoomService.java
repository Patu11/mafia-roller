package com.github.patu11.mafiaroller.room;

import com.github.patu11.mafiaroller.CodeGenerator;
import com.github.patu11.mafiaroller.NotFoundException;
import com.github.patu11.mafiaroller.dto.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional(readOnly = true)
    public List<RoomDTO> getAllRooms() {
        List<RoomDTO> list = this.roomRepository.findAll().stream()
                .map(room -> new RoomDTO(room.getCode(), room.getName()))
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new NotFoundException("No rooms found.");
        }
        return list;
    }

    @Transactional(readOnly = true)
    public RoomDTO getRoomByCode(String code) {
        Optional<Room> optional = this.roomRepository.findById(code);
        if (optional.isEmpty()) {
            throw new NotFoundException("Room not found.");
        }

        return new RoomDTO(optional.get().getCode(), optional.get().getName());
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

    private boolean isAvailable(String code) {
        Optional<String> dbCode = this.roomRepository.findCode(code);
        return dbCode.isEmpty();
    }
}
