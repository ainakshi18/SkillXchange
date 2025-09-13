//package com.SkillExchange.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class Webconfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        // Enable a simple in-memory message broker for "/topic"
//        registry.enableSimpleBroker("/topic");
//        // Define a prefix for application messages
//        registry.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // Register a WebSocket endpoint "/ws" for client connections
//        registry.addEndpoint("/ws")
//                // Allow connections only from this frontend (CORS policy)
//                .setAllowedOrigins("http://localhost:5173")
//                .withSockJS(); // Fallback options if WebSocket is not supported
//    }
//}
////@Configuration
////@EnableWebSocketMessageBroker
////public class Webconfig implements WebSocketMessageBrokerConfigurer{
////	
////	@Override
////	public void configureMessageBroker(MessageBrokerRegistry registry) {
////		registry.enableSimpleBroker("/topic");
////		registry.setApplicationDestinationPrefixes("/app");
////		
////	}
////	
////	@Override
////	public void registerStompEndpoints(StompEndpointRegistry registry) {
//////		registry.addEndpoint("/server1")
////		registry.addEndpoint("ws")
////        .setAllowedOrigins("http://localhost:5173")
////		.withSockJS();
////	}
////
////}
//
//
