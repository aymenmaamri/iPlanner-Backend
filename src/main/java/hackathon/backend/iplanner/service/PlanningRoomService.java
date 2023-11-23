package hackathon.backend.iplanner.service;

import hackathon.backend.iplanner.dto.PlanningRoomDto;
import hackathon.backend.iplanner.factories.PlanningRoomFactoryImpl;
import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.PlanningStory;
import hackathon.backend.iplanner.repository.PlanningRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    public PlanningRoom joinPlanningRoom(String username, String roomName){
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new IllegalStateException("Room not found"));

        // check if the user is already joined
        if(planningRoom.isUserAlreadyJoined(username)) return null;

        planningRoom.getJoinedUsers().add(username);
        PlanningRoom joined = planningRoomRepository.save(planningRoom);
        return joined;
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

    public List<PlanningStory> getStoriesInRoom(String roomName) {
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName).orElseThrow(() -> new IllegalStateException("Room not found"));
        return planningRoom.getPlanningStories();
    }
}
