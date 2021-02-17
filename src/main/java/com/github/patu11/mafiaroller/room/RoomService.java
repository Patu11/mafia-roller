package com.github.patu11.mafiaroller.room;

import com.github.patu11.mafiaroller.CodeGenerator;
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
        return this.roomRepository.findAll().stream()
                .map(room -> new RoomDTO(room.getCode(), room.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addRoom(RoomData roomData) {
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
