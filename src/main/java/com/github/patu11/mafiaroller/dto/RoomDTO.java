package com.github.patu11.mafiaroller.dto;

import com.github.patu11.mafiaroller.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
	private String code;

	private String name;

	private boolean started;

	private List<UserDTO> users;
}
