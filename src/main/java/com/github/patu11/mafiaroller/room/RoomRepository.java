package com.github.patu11.mafiaroller.room;

import com.github.patu11.mafiaroller.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    @Query(value = "SELECT code FROM room WHERE code = :code", nativeQuery = true)
    Optional<String> findCode(@Param("code") String code);
}
