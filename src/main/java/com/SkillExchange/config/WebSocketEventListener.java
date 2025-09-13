//package TeleMed.TeleMed.config;
//
//
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import com.groupchat.Groupchat.models.ChatMessage;
//import com.groupchat.Groupchat.models.MessageType;
//@Component
//public class WebSocketEventListener {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    // Constructor-based dependency injection
//    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        
//        if (username != null) {
//            ChatMessage message = new ChatMessage.Builder()
//                    .setType(MessageType.CHAT)
//                    .setContent("Hello")
//                    .setSender("User1")
//                    .build();
//
//            messagingTemplate.convertAndSend("/topic/public", message);
//        }
//    }
//}
//
