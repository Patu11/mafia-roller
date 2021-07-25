package com.github.patu11.mafiaroller;

import com.github.patu11.mafiaroller.dto.UserDTO;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RoleGenerator {

	private final int numberOfPlayers;
	private final List<UserDTO> users;
	private final UserDTO user;

	public RoleGenerator(List<UserDTO> users) {
		this.users = users;
		Optional<UserDTO> usr = this.users.stream().filter(u -> u.getRole().equals(MafiaRoles.HOST.name())).findFirst();
		this.user = usr.orElse(null);
		this.users.removeIf(u -> u.getRole().equals(MafiaRoles.HOST.name()));
		this.numberOfPlayers = users.size();
	}

	public List<UserDTO> getGeneratedUsersRoles() {
		List<String> freeRoles = this.generateRoles(this.numberOfPlayers);

		for (UserDTO u : this.users) {
			if (!u.getRole().equals(MafiaRoles.HOST.name())) {
				int index = new Random().nextInt(freeRoles.size());
				String role = freeRoles.remove(index);
				u.setRole(role);
			}
		}

		this.users.add(this.user);
		return this.users;
	}

	private List<String> generateRoles(int number) {
		List<String> freeRoles = new ArrayList<>();

		int numberOfMafia = (int) Math.round(Math.sqrt(number));
		int numberOfVilligers = number - numberOfMafia - 3;

		freeRoles.add(MafiaRoles.GUARD.name());
		freeRoles.add(MafiaRoles.POLICEMAN.name());
		freeRoles.add(MafiaRoles.FREAK.name());

		for (int i = 0; i < numberOfMafia; i++) {
			freeRoles.add(MafiaRoles.MAFIA.name());
		}

		for (int i = 0; i < numberOfVilligers; i++) {
			freeRoles.add(MafiaRoles.VILLAGER.name());
		}

		return freeRoles;
	}
}
