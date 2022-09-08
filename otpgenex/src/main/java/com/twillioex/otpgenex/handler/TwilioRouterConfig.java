package com.twillioex.otpgenex.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TwilioRouterConfig {

	@Autowired
	private TwilioHandler twilioHandler;
	
	@Bean
	public RouterFunction<ServerResponse> handleSMS(){
		return RouterFunctions.route().
				POST("/router/sendOTPSMS", twilioHandler::sendSMS)
				.POST("/router/validateOTP", twilioHandler::validateOTP)
				.build();
	}
	
	
}
