package com.github.patu11.mafiaroller.user;

import com.github.patu11.mafiaroller.room.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "player")
public class User {
	@Id
	private String username;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_code")
	private Room room;

	private String role;

	private boolean dead;

	public User() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
