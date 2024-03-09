package hackathon.backend.iplanner.config;

import hackathon.backend.iplanner.enums.EventType;
import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.events.LeaderDisconnectedEvent;
import hackathon.backend.iplanner.model.events.LeaveRoomEvent;
import hackathon.backend.iplanner.service.PlanningRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;
    private final PlanningRoomService planningRoomService;

    WebSocketEventListener(SimpMessageSendingOperations messageSendingOperations, PlanningRoomService planningRoomService){
      this.messageSendingOperations = messageSendingOperations;
      this.planningRoomService = planningRoomService;
    }

   @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ){
       StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
       // put other attributes in the controller method
       String username = (String) stompHeaderAccessor.getSessionAttributes().get("username");
       String roomName = (String) stompHeaderAccessor.getSessionAttributes().get("roomName");

       if (username != null){
           // TODO: fix
           LeaveRoomEvent leaveRoomEvent = LeaveRoomEvent.builder().build();
           leaveRoomEvent.setRoomName(roomName);
           leaveRoomEvent.setSender(username);
           leaveRoomEvent.setType(EventType.LEAVE_ROOM);
           // Call the service method to remove the user from the planning room
           PlanningRoom planningRoom = planningRoomService.removeUserFromPlanningRoom(leaveRoomEvent);
           leaveRoomEvent.setCurrentPlayers(planningRoom.getCurrentPlayers());

           messageSendingOperations.convertAndSend("/topic/" + roomName, leaveRoomEvent);

           // if leader has left planning
           if(planningRoom.isLeader(username)) {
              LeaderDisconnectedEvent leaderDisconnectedEvent = planningRoomService.handleLeaderDisconnect(roomName, username);
              messageSendingOperations.convertAndSend("/topic/" + roomName, leaderDisconnectedEvent);
           }
       }
    }
}
