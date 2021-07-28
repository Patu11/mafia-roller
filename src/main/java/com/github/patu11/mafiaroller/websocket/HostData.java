package com.github.patu11.mafiaroller.websocket;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HostData {

	@NotNull
	private String currentHost;

	@NotNull
	private String newHost;
}
