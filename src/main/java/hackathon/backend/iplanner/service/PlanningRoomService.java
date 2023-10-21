package hackathon.backend.iplanner.service;

import hackathon.backend.iplanner.data.PlanningRoom;
import hackathon.backend.iplanner.data.PlanningRooms;
import hackathon.backend.iplanner.data.User;
import hackathon.backend.iplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlanningRoomService {
    @Autowired
    private PlanningRooms planningRooms;
    @Autowired
    private UserService userService;

    public PlanningRoom createPlanningRoom(String roomName, String userId){
        boolean roomExists = planningRooms.rooms.stream()
                .map(planningRoom -> planningRoom.getRoomName())
                .anyMatch(existingName -> existingName.equals(roomName));

       // boolean userExists = userService.userExists(userId);

        //if(roomExists || !userExists) return null;

        String username = userService.getUserById(userId).getUsername();
        PlanningRoom newPlanningRoom = new PlanningRoom(roomName, userId, username);
        planningRooms.rooms.add(newPlanningRoom);
        return newPlanningRoom;
    }

    public ArrayList<PlanningRoom> getPlanningRooms(){
        if(planningRooms.rooms.size() > 0) {
            return planningRooms.rooms;
        } else {
            return new ArrayList<>();
        }
    }



    public PlanningRoom joinPlanningRoom(String username, String roomId){
        // get room
        PlanningRoom roomToJoin = getRoomById(roomId);
        // get User
        User userToJoin = userService.getUserByUsername(username);

        if(roomToJoin == null || userToJoin == null) return null;

        // check if the user is already joined
        boolean hasJoined = roomToJoin.isUserInRoom(userToJoin.getUsername());
        if(hasJoined) return roomToJoin;


        String added = roomToJoin.addUserToRoom(userToJoin.getUsername());

        System.out.println("user to add" + added);
        System.out.println(roomToJoin.users.size());
        return roomToJoin;
    }


    // Helpers
    PlanningRoom getRoomById(String roomId){
        return planningRooms.rooms.stream().filter(room -> room.roomId.equals(roomId)).findFirst().get();
    }
}
