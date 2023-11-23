package hackathon.backend.iplanner.factories;

import hackathon.backend.iplanner.dto.PlanningRoomDto;
import hackathon.backend.iplanner.model.PlanningRoom;

public interface PlanningRoomFactory {
    PlanningRoom createPlanningRoom(PlanningRoomDto planningRoomDto, String username);
}
