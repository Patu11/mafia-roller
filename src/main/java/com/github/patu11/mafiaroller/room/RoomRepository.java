package com.github.patu11.mafiaroller.room;

import com.github.patu11.mafiaroller.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
}
