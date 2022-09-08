package com.twillioex.otpgenex.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twillioex.otpgenex.config.TwilioConfig;
import com.twillioex.otpgenex.dto.PasswordResetRequestDto;
import com.twillioex.otpgenex.dto.PasswordResetResponseDto;
import com.twillioex.otpgenex.enums.TwilioEnum;

import reactor.core.publisher.Mono;

@Service
public class TwilioService {

	@Autowired
	private TwilioConfig twilioConfig;
	
	Map<String, String> otpMap = new HashMap<String, String>();
	
	public Mono<PasswordResetResponseDto> sendOtpForPasswordReset(PasswordResetRequestDto passwordResetRequestDto){
		PasswordResetResponseDto passwordResetResponseDto = null;
		try {
		PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
		PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
		String otp = otpGenerate();
		String otpMessage = "Dear Customer, your otp is ## " +otp+ " # Use this passcode to complete your transaction, please do not share otp with anyone. Thank you!";
		Message message = Message.creator(
                to,//to
                from,
                otpMessage)
            .create();
		otpMap.put(passwordResetRequestDto.getUserName(), otp);
		passwordResetResponseDto = new PasswordResetResponseDto(otpMessage, TwilioEnum.Deliverd);
		}catch(Exception e) {
			passwordResetResponseDto = new PasswordResetResponseDto(e.getMessage(), TwilioEnum.NotDeliverd);
		}
		return Mono.just(passwordResetResponseDto);
	}
	
	private String otpGenerate() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999));
	}
	
	public Mono<String> validateOtp(String userInputOtp, String userName){
		if(userInputOtp.equals(otpMap.get(userName))) {
			otpMap.remove(userName, userInputOtp);
			return Mono.just("OTP is validated! Now You can proceed with next step :)");
		}else {
			return Mono.error(new IllegalArgumentException("Invaild OTP! Please check once or resend it, Thank You."));
		}
	}
	
}
