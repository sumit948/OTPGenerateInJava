package com.twillioex.otpgenex.dto;

import com.twillioex.otpgenex.enums.TwilioEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponseDto {

	private String otpMessage;
	
	private TwilioEnum otpStatus;
}
