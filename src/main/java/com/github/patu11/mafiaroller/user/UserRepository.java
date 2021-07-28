package com.github.patu11.mafiaroller.user;

import com.github.patu11.mafiaroller.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	List<User> findAllByRoomCode(String roomCode);

	List<User> findAllByRoomCodeOrderByUsername(String roomCode);
}
