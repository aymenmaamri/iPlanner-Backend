package hackathon.backend.iplanner.service;

import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.Story;
import hackathon.backend.iplanner.repository.PlanningRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoriesService {

    private final PlanningRoomRepository planningRoomRepository;


    public StoriesService(PlanningRoomRepository planningRoomRepository) {
        this.planningRoomRepository = planningRoomRepository;
    }

    public Story createStory(Story story, String roomName, String username){
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new IllegalStateException("Room not found"));

        //TODO: CHECK IF THE USER IS OWNER

        planningRoom.getPlanningStories().add(story);
        Story added =  planningRoomRepository.save(planningRoom).getPlanningStories().get(0);
        return added;
    }
}
