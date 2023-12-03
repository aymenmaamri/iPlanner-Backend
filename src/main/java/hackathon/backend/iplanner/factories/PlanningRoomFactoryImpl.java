package hackathon.backend.iplanner.factories;

import hackathon.backend.iplanner.dto.PlanningRoomDto;
import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.Story;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class PlanningRoomFactoryImpl implements PlanningRoomFactory{

    private final ModelMapper modelMapper;

    public PlanningRoomFactoryImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PlanningRoom createPlanningRoom(PlanningRoomDto planningRoomDto, String username) {
        PlanningRoom planningRoom = modelMapper.map(planningRoomDto, PlanningRoom.class);
        planningRoom.setCreationTime(new Date());
        planningRoom.setJoinedUsers(new ArrayList<String>());
        planningRoom.setPlanningStories(new ArrayList<Story>());
        planningRoom.setKeepAliveDurationSeconds(7200);
        planningRoom.setRoomOwner(username);
        return planningRoom;
    }
}
