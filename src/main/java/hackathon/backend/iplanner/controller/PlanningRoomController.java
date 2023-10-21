package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.dto.PlanningRoomDto;
import hackathon.backend.iplanner.service.PlanningRoomService;
import hackathon.backend.iplanner.model.PlanningRoom;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PlanningRoomController {
    private final PlanningRoomService planningRoomService;

    public PlanningRoomController(PlanningRoomService planningRoomService) {
        this.planningRoomService = planningRoomService;
    }

    @PostMapping("/planningRoom")
    public ResponseEntity<String> createPlanningRoom(@Valid @RequestBody PlanningRoomDto planningRoomDto){
        PlanningRoom planningRoom = planningRoomService.getPlanningRoom(planningRoomDto.getRoomName());
        if(planningRoom != null) return ResponseEntity.status(HttpStatus.CONFLICT).body("a planning room with this name already exists");
        // Call the service to create the planning room
        planningRoomService.createPlanningRoom(planningRoomDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("planning room created");
    }

    /*@GetMapping("/planning-room")
    public ResponseEntity<PlanningRoomDto> getPlanningRoom(){
        ArrayList<PlanningRoom> rooms = planningRoomService.getPlanningRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/planning-room/join")
    public ResponseEntity<PlanningRoom> joinPlanningRoom(@RequestParam String roomId, @RequestParam String username){
       PlanningRoom joinedRoom = planningRoomService.joinPlanningRoom(username, roomId);

       if(joinedRoom != null) return ResponseEntity.ok(joinedRoom);
       return ResponseEntity.badRequest().body(null);
    }*/
}
