package com.github.patu11.mafiaroller.room;

import com.github.patu11.mafiaroller.dto.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    public List<RoomDTO> getAllRooms() {
        return this.roomRepository.findAll().stream()
                .map(room -> new RoomDTO(room.getCode(), room.getName()))
                .collect(Collectors.toList());
    }
}
