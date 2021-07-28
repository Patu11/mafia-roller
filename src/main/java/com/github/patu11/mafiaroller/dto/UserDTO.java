package com.github.patu11.mafiaroller.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private String username;
	private String roomCode;
	private String role;
	private boolean dead;
	private boolean started;
}
