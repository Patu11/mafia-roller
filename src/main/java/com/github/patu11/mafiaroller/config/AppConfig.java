package com.github.patu11.mafiaroller.config;

import com.github.patu11.mafiaroller.dto.RoomDTO;
import com.github.patu11.mafiaroller.room.Room;
import com.github.patu11.mafiaroller.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class AppConfig {
	private final RoomService roomService;

	@Autowired
	public AppConfig(RoomService roomService) {
		this.roomService = roomService;
	}

	@Scheduled(fixedDelay = 5000)
	public void deleteEmptyRooms() {
		this.roomService.deleteEmptyRooms();
	}

}
