package hackathon.backend.iplanner.config;

import hackathon.backend.iplanner.enums.EventType;
import hackathon.backend.iplanner.model.events.RoomEvent;
import hackathon.backend.iplanner.service.PlanningRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;
    private final PlanningRoomService planningRoomService;

   @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ){
       StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
       // put other attributes in the controller method
       String username = (String) stompHeaderAccessor.getSessionAttributes().get("username");
       String roomName = (String) stompHeaderAccessor.getSessionAttributes().get("roomName");
       if (username != null){

           // Call the service method to remove the user from the planning room
           planningRoomService.removeUserFromPlanningRoom(username, roomName);


           var roomEvent = RoomEvent.builder()
                   .type(EventType.LEAVE_ROOM)
                   .sender(username)
                   .build();
           messageSendingOperations.convertAndSend("/topic/" + roomName, roomEvent);
       }
    }
}
