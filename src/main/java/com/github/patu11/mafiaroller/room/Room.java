package com.github.patu11.mafiaroller.room;

import com.github.patu11.mafiaroller.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room {
	@Id
	private String code;

	@Column(name = "name")
	private String name;

	//	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
	@OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
	private Set<User> users = new HashSet<>();
	
	private boolean started;

	public Room(String name) {
		this.name = name;
	}

	public Room() {
	}

	public void addUser(User user) {
		users.add(user);
		user.setRoom(this);
	}

	public void deleteUser(User user) {
		users.remove(user);
		user.setRoom(null);
	}
}
