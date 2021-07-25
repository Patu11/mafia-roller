package com.github.patu11.mafiaroller.user;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

	@NotNull
	private String username;

	@NotNull
	private String roomCode;

	@NotNull
	private String role;

	@NotNull
	private boolean dead;

}
