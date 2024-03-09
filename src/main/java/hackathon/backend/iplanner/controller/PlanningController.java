package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.enums.EventType;
import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.events.*;
import hackathon.backend.iplanner.service.PlanningRoomService;
import hackathon.backend.iplanner.service.UserStoriesService;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller

public class PlanningController {

    private final SimpMessagingTemplate messagingTemplate;
    private final PlanningRoomService planningRoomService;
    private final UserStoriesService userStoriesService;

    public PlanningController(SimpMessagingTemplate messagingTemplate, PlanningRoomService planningRoomService, UserStoriesService userStoriesService) {
        this.messagingTemplate = messagingTemplate;
        this.planningRoomService = planningRoomService;
        this.userStoriesService = userStoriesService;
    }

    @MessageMapping("/user/{username}/ping")
    public void sendEvent(@Payload PingEvent pingEvent, @DestinationVariable String username, SimpMessageHeaderAccessor headerAccessor){
        String roomName = pingEvent.getRoomName();
        //String username = (String) headerAccessor.getSessionAttributes().get("username");
        System.out.println("ping message received from " + username);

        PongEvent pongEvent = PongEvent.builder().build();
        pongEvent.setType(EventType.PONG);

        // message sent to user/{username}/pong
        messagingTemplate.convertAndSendToUser(username, "/pong", pongEvent);
    }

    @MessageMapping("/send")
    public RoomEvent sendEvent(@Payload RoomEvent roomEvent){
        planningRoomService.saveEvent(roomEvent);
        messagingTemplate.convertAndSend("/topic/" + roomEvent.getRoomName(), roomEvent);
        return roomEvent;
    }

    @MessageMapping("/play-card")
    public PlayCardEvent sendEvent(@Payload PlayCardEvent playCardEvent){
        System.out.println(playCardEvent.getEventId());
        // Todo: should i forward then save event, or other way around
        planningRoomService.saveEvent(playCardEvent);
        messagingTemplate.convertAndSend("/topic/" + playCardEvent.getRoomName(), playCardEvent);
        return playCardEvent;
    }

    @MessageMapping("/create-story")
    public CreateStoryEvent sendEvent(@Payload CreateStoryEvent createStoryEvent){
        planningRoomService.saveEvent(createStoryEvent);
        messagingTemplate.convertAndSend("/topic/" + createStoryEvent.getRoomName(), createStoryEvent);
        return createStoryEvent;
    }

    @MessageMapping("/join-room")
    public JoinRoomEvent addUser(@Payload JoinRoomEvent joinRoomEvent, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", joinRoomEvent.getSender());
        headerAccessor.getSessionAttributes().put("roomName", joinRoomEvent.getRoomName());
        // should this be executed in this order
        PlanningRoom planningRoom = planningRoomService.joinPlanningRoom(joinRoomEvent);
        // set the current users in the msg
        // capture the current state of the room (players, actions, stories)
        joinRoomEvent.setCurrentPlayers(planningRoom.getCurrentState());
        joinRoomEvent.setStoryInEstimation(planningRoom.getCurrentStoryInEstimation());
        //joinRoomEvent.setStories(planningRoom.getStories());
        messagingTemplate.convertAndSend("/topic/" + joinRoomEvent.getRoomName(), joinRoomEvent);
        return joinRoomEvent;
    }

    @MessageMapping("/set-ready-status")
    public PlayerReadinessEvent setPlayerReadiness(@Payload PlayerReadinessEvent playerReadinessEvent){
        planningRoomService.saveEvent(playerReadinessEvent);
        messagingTemplate.convertAndSend("/topic/" + playerReadinessEvent.getRoomName(), playerReadinessEvent);
        return playerReadinessEvent;
    }

    @MessageMapping("/show-cards")
    public ShowCardsEvent showCards(@Payload ShowCardsEvent showCardsEvent){
        planningRoomService.saveEvent(showCardsEvent);
        messagingTemplate.convertAndSend("/topic/" + showCardsEvent.getRoomName(), showCardsEvent);
        return showCardsEvent;
    }

    @MessageMapping("/estimate-story")
    public EstimateStoryEvent estimateStory(@Payload EstimateStoryEvent estimateStoryEvent){
        planningRoomService.saveEvent(estimateStoryEvent);
        estimateStoryEvent.setEstimatedRoomStories(userStoriesService.getRoomUserStories(estimateStoryEvent.getRoomName()));
        messagingTemplate.convertAndSend("/topic/" + estimateStoryEvent.getRoomName(), estimateStoryEvent);
        return estimateStoryEvent;
    }
}
