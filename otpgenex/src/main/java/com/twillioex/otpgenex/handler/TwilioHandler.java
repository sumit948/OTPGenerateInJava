package com.twillioex.otpgenex.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.twillioex.otpgenex.dto.PasswordResetRequestDto;
import com.twillioex.otpgenex.service.TwilioService;

import reactor.core.publisher.Mono;

@Component
public class TwilioHandler {

	@Autowired
	private TwilioService twilioService;
	
	public Mono<ServerResponse> sendSMS(ServerRequest serverRequest){
		return serverRequest.bodyToMono(PasswordResetRequestDto.class)
				.flatMap(dto -> twilioService.sendOtpForPasswordReset(dto))
				.flatMap(dto -> ServerResponse.status(HttpStatus.OK)
				.body(BodyInserters.fromValue(dto)));
	}
	
	public Mono<ServerResponse> validateOTP(ServerRequest serverRequest){
		return serverRequest.bodyToMono(PasswordResetRequestDto.class)
				.flatMap(dto -> twilioService.validateOtp(dto.getOtp(), dto.getUserName()))
				.flatMap(dto -> ServerResponse.status(HttpStatus.OK).bodyValue(dto));
	}
}
