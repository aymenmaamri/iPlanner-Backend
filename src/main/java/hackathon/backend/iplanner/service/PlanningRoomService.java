package hackathon.backend.iplanner.service;

import hackathon.backend.iplanner.dto.PlanningRoomDto;
import hackathon.backend.iplanner.exception.PlanningRoomAlreadyExistsException;
import hackathon.backend.iplanner.model.*;
import hackathon.backend.iplanner.model.events.*;
import hackathon.backend.iplanner.repository.PlanningRoomRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanningRoomService {
    private final PlanningRoomRepository planningRoomRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    PlanningRoomService(PlanningRoomRepository planningRoomRepository, ModelMapper modelMapper, UserService userService){
        this.planningRoomRepository = planningRoomRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public PlanningRoom getPlanningRoom(String roomName){
        Optional<PlanningRoom> planningRoom = planningRoomRepository.findByRoomName(roomName);
        if (planningRoom.isPresent()) return planningRoom.get();
        return null;
    }

    public PlanningRoom createPlanningRoom(PlanningRoomDto planningRoomDto){
        // TODO: maybe implement the logic for user verification here?
        PlanningRoom planningRoom = getPlanningRoom(planningRoomDto.getRoomName());
        if (planningRoom != null) {
            throw new PlanningRoomAlreadyExistsException();
        }
        planningRoom = modelMapper.map(planningRoomDto, PlanningRoom.class);
        planningRoom.setUserEvents(new HashMap<String, UserEvent>());
        return planningRoomRepository.save(planningRoom);
    }

    public List<PlanningRoom> getPlanningRooms(){
        return planningRoomRepository.findAll();
    }

    // TODO: further think about joining room logic and how it is called in the frontend
    public PlanningRoom joinPlanningRoom(JoinRoomEvent joinRoomEvent){
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(joinRoomEvent.getRoomName())
                .orElseThrow(() -> new IllegalStateException("Room not found"));

        // check if the user is already joined
        if(planningRoom.isUserCurrentlyJoined(joinRoomEvent.getSender())) {
            return planningRoom;
        }

        // check if there is a free pos for user
        if(planningRoom.isJoinable()){
            Map<String, UserEvent> userEvents = planningRoom.getUserEvents();
            // user already exists in history of room
            if(userEvents.get(joinRoomEvent.getSender()) != null) {
                userEvents.get(joinRoomEvent.getSender()).getEventList().add(joinRoomEvent);
            } else {
                User user = userService.getUserByUsername(joinRoomEvent.getSender());
                List<RoomEvent> events = new ArrayList<RoomEvent>();
                events.add(joinRoomEvent);
                userEvents.put(joinRoomEvent.getSender(), new UserEvent(new ObjectId().toString(), user, events));
            }
            PlanningRoom joined = planningRoomRepository.save(planningRoom);
            return joined;
        } else {
            // handle the appropriate response to user
            throw new IllegalStateException("The planning room is full.");
        }
    }

    // Todo: is this really needed in the backend?
   /* public PlanningRoom assignPosition(String username, String roomName) {
        // Check if the user is already assigned a position
        // TODO: think about this
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName).orElseThrow(() ->
                new IllegalStateException("Room not found"));
        // TODO: simplify
        if (!planningRoom.isAlreadyAssignedAPosition(username)) {
            // Find an available position and assign it to the user
            String availablePositionKey = planningRoom.getPlayerPositions().keySet()
                    .stream()
                    .filter(positionKey -> !planningRoom.getPlayerPositions().get(positionKey).isAssigned())
                    .findFirst()
                    .orElse(null);

            if (availablePositionKey != null) {
                PlayerPositionOnBoard playerPosition = planningRoom.getPlayerPositions().get(availablePositionKey);
                playerPosition.setUsername(username);
                playerPosition.setAssigned(true);
                planningRoomRepository.save(planningRoom);
                return planningRoom;
            } else {
                // should not happen
                // Handle the case where there are no available positions
                throw new IllegalStateException("No available positions to assign.");
            }
        }
        return null;
    }*/

    public void deletePlanningRoom(String roomName, String initiatorUsername) {
        Optional<PlanningRoom> optionalPlanningRoom = planningRoomRepository.findByRoomName(roomName);
        PlanningRoom planningRoom = optionalPlanningRoom.orElseThrow(() ->
                new IllegalStateException("Room not found"));
        if (planningRoom.isOwner(initiatorUsername)){
            planningRoomRepository.deleteByRoomName(roomName);
            return;
        }
        throw new IllegalStateException("User is not room owner");
    }

    public List<CreateStoryEvent> getRoomStories(String roomName) {
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName).orElseThrow(() -> new IllegalStateException("Room not found"));
        return planningRoom.getStories();
    }

    public PlanningRoom removeUserFromPlanningRoom(LeaveRoomEvent leaveRoomEvent) {
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(leaveRoomEvent.getRoomName())
                .orElseThrow(() -> new IllegalStateException("Room not found"));

        Map<String, UserEvent> userEvents = planningRoom.getUserEvents();
        if(userEvents.get(leaveRoomEvent.getSender()) != null) {
            userEvents.get(leaveRoomEvent.getSender()).getEventList().add(leaveRoomEvent);
        }
        planningRoomRepository.save(planningRoom);
        return planningRoom;
    }

   // todo: duplicate logic, should this be here or in the class method
    public boolean isUserRoomOwner(String roomName, String username) {
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new IllegalStateException("Room not found"));
        if(planningRoom.getRoomOwner() == username) return true;
        return false;
    }

    // Todo
    // Here i am getting the Map and directly modifying it,
    // should i first create a copy of the map to achieve immutability
    // maybe extract logic to avoid duplication
    public void saveEvent(RoomEvent roomEvent) {
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomEvent.getRoomName()).orElseThrow(() -> new IllegalStateException("Room not found"));
        Map<String, UserEvent> userEvents = planningRoom.getUserEvents();
        UserEvent userEvent = userEvents.get(roomEvent.getSender());
        if(userEvent != null) {
            userEvent.getEventList().add(roomEvent);
        }
        // Todo: can i only modify the event log without saving the entire planning room object
        planningRoomRepository.save(planningRoom);
    }

}
