package com.github.patu11.mafiaroller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorBody {
    private int status;
    private String message;
}
