package hackathon.backend.iplanner.data;

import hackathon.backend.iplanner.data.PlanningRoom;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PlanningRooms {

    public ArrayList<PlanningRoom> rooms;

    public PlanningRooms() {
        this.rooms = new ArrayList<PlanningRoom>();
    }

    public void setPlanningRooms(ArrayList<PlanningRoom> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<PlanningRoom> getPlanningRooms() {
        return rooms;
    }

}
