package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.service.PlanningRoomService;
import hackathon.backend.iplanner.data.PlanningRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@CrossOrigin(origins = "*")
public class PlanningRoomController {
    @Autowired
    private PlanningRoomService planningRoomService;

    @PostMapping("/planning-room")
    public ResponseEntity<PlanningRoom> createPlanningRoom(@RequestParam String roomName, @RequestParam String userId){

        // Call the service to create the planning room
        PlanningRoom created = planningRoomService.createPlanningRoom(roomName, userId);
        //String response = planningRoom.getRoomId();
        if(created != null) {
            return ResponseEntity.ok(created);
        }else{
            // 3akshaa error
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/planning-room")
    public ResponseEntity<ArrayList<PlanningRoom>> getPlanningRooms(){
        ArrayList<PlanningRoom> rooms = planningRoomService.getPlanningRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/planning-room/join")
    public ResponseEntity<PlanningRoom> joinPlanningRoom(@RequestParam String roomId, @RequestParam String username){
       PlanningRoom joinedRoom = planningRoomService.joinPlanningRoom(username, roomId);

       if(joinedRoom != null) return ResponseEntity.ok(joinedRoom);
       return ResponseEntity.badRequest().body(null);
    }
}
