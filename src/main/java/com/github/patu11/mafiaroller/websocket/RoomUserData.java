package com.github.patu11.mafiaroller.websocket;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomUserData {
	@NotNull
	private String name;

	@NotNull
	private String code;

	@NotNull
	private String username;
}
