package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.PlayerPositionOnBoard;
import hackathon.backend.iplanner.model.events.JoinRoomEvent;
import hackathon.backend.iplanner.model.events.RoomEvent;
import hackathon.backend.iplanner.service.PlanningRoomService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final PlanningRoomService planningRoomService;

    public SocketController(SimpMessagingTemplate messagingTemplate, PlanningRoomService planningRoomService) {
        this.messagingTemplate = messagingTemplate;
        this.planningRoomService = planningRoomService;
    }


    @MessageMapping("/send")
    public RoomEvent sendEvent(@Payload RoomEvent roomEvent){
        messagingTemplate.convertAndSend("/topic/" + roomEvent.getRoomName(), roomEvent);
        return roomEvent;
    }

    @MessageMapping("/joinRoom")
    public JoinRoomEvent addUser(@Payload JoinRoomEvent joinRoomEvent, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", joinRoomEvent.getSender());
        headerAccessor.getSessionAttributes().put("roomName", joinRoomEvent.getRoomName());
        // should this be executed in this order
        planningRoomService.joinPlanningRoom(joinRoomEvent.getSender(), joinRoomEvent.getRoomName());
        PlanningRoom updatedPlanningRoom = planningRoomService.assignPosition(joinRoomEvent.getSender(), joinRoomEvent.getRoomName());

        // exclude the sender position from the response, the sender only need to know th position of other players
        if(updatedPlanningRoom != null) {
            joinRoomEvent.setPayload(updatedPlanningRoom.getPlayerPositions());
        }

        messagingTemplate.convertAndSend("/topic/" + joinRoomEvent.getRoomName(), joinRoomEvent);
        return joinRoomEvent;
    }
}
