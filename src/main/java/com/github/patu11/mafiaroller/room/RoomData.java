package com.github.patu11.mafiaroller.room;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomData {

	@NotNull
	private String name;

	@NotNull
	private String code;

	@NotNull
	boolean started;
}
