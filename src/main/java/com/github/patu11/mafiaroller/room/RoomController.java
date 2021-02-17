package com.github.patu11.mafiaroller.room;

import com.github.patu11.mafiaroller.dto.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private RoomService roomService;

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
    public ResponseEntity<?> createRoom(@RequestBody RoomData roomData) {
        this.roomService.addRoom(roomData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
