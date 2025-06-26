package com.hexa.exceptions;

import java.time.Instant;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ApiError {

	private Instant timestamp = Instant.now();
	
	private int status;
	private String error;
	private String message;
	private String path;
}
