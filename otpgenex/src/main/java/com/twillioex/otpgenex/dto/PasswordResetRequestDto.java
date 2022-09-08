package com.twillioex.otpgenex.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto {

	private String phoneNumber;
	
	private String userName;
	
	private String otp;
}
