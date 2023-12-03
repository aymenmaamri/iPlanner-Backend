package hackathon.backend.iplanner.service;

import hackathon.backend.iplanner.dto.PlanningRoomDto;
import hackathon.backend.iplanner.factories.PlanningRoomFactoryImpl;
import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.PlayerPositionOnBoard;
import hackathon.backend.iplanner.model.Story;
import hackathon.backend.iplanner.repository.PlanningRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanningRoomService {
    private final PlanningRoomRepository planningRoomRepository;
    private final PlanningRoomFactoryImpl planningRoomFactory;

    PlanningRoomService(PlanningRoomRepository planningRoomRepository, PlanningRoomFactoryImpl planningRoomFactory){
        this.planningRoomRepository = planningRoomRepository;
        this.planningRoomFactory = planningRoomFactory;
    }

    public PlanningRoom getPlanningRoom(String roomName){
        Optional<PlanningRoom> planningRoom = planningRoomRepository.findByRoomName(roomName);
        if (planningRoom.isPresent()) return planningRoom.get();
        return null;
    }

    public PlanningRoom createPlanningRoom(PlanningRoomDto planningRoomDto, String username){
        // TODO: maybe implement the logic for user verification here?
        PlanningRoom planningRoom = planningRoomFactory.createPlanningRoom(planningRoomDto, username);
        planningRoomRepository.save(planningRoom);
        return planningRoom;
    }

    public List<PlanningRoom> getPlanningRooms(){
        return planningRoomRepository.findAll();
    }

    // TODO: further think about joining room logic and how it is called in the frontend
    public PlanningRoom joinPlanningRoom(String username, String roomName){
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new IllegalStateException("Room not found"));

        // check if the user is already joined
        if(planningRoom.isUserAlreadyJoined(username)) return planningRoom;

        // check if there is a free pos for user
        if(planningRoom.canJoinRoom()){
            planningRoom.getJoinedUsers().add(username);
            PlanningRoom joined = planningRoomRepository.save(planningRoom);
            return joined;
        } else {
            // handle the appropriate response to user
            throw new IllegalStateException("The planning room is full.");
        }
    }

    public PlanningRoom assignPosition(String username, String roomName) {
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
    }

    public void deletePlanningRoom(String roomName, String initiatorUsername) {
        Optional<PlanningRoom> optionalPlanningRoom = planningRoomRepository.findByRoomName(roomName);
        PlanningRoom planningRoom = optionalPlanningRoom.orElseThrow(() ->
                new IllegalStateException("Room not found"));
        if (planningRoom.isRoomOwner(initiatorUsername)){
            planningRoomRepository.deleteByRoomName(roomName);
            return;
        }
        throw new IllegalStateException("User is not room owner");
    }

    public List<Story> getStoriesInRoom(String roomName) {
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName).orElseThrow(() -> new IllegalStateException("Room not found"));
        return planningRoom.getPlanningStories();
    }

    public void removeUserFromPlanningRoom(String username, String roomName) {
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new IllegalStateException("Room not found"));
        planningRoom.removePlayer(username);
        planningRoom.freeBoardPosition(username);
        planningRoomRepository.save(planningRoom);
    }

    public boolean isUserRoomOwner(String roomName, String username) {
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new IllegalStateException("Room not found"));
        if(planningRoom.getRoomOwner() == username) return true;
        return false;
    }
}
