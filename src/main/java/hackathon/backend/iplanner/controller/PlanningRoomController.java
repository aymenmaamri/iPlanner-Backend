package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.dto.PlanningRoomDto;
import hackathon.backend.iplanner.dto.UserDto;
import hackathon.backend.iplanner.model.User;
import hackathon.backend.iplanner.service.PlanningRoomService;
import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PlanningRoomController {
    private final PlanningRoomService planningRoomService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public PlanningRoomController(PlanningRoomService planningRoomService, UserService userService, ModelMapper modelMapper) {
        this.planningRoomService = planningRoomService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/planning-room")
    public ResponseEntity<String> createPlanningRoom(@Valid @RequestBody PlanningRoomDto planningRoomDto){
        PlanningRoom planningRoom = planningRoomService.getPlanningRoom(planningRoomDto.getRoomName());
        if(planningRoom != null) return ResponseEntity.status(HttpStatus.CONFLICT).body("a planning room with this name already exists");
        // Call the service to create the planning room
        String roomName = planningRoomService.createPlanningRoom(planningRoomDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomName);
    }

    @GetMapping("/planning-room")
    public ResponseEntity<List<PlanningRoomDto>> getPlanningRoom(){
        List<PlanningRoom> rooms = planningRoomService.getPlanningRooms();
        List<PlanningRoomDto> roomDtos = rooms.stream()
                .map(room -> modelMapper.map(room, PlanningRoomDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomDtos);
    }

    @GetMapping("/planning-room/join")
    public ResponseEntity<String> joinPlanningRoom(@RequestParam String roomName, @RequestParam String username){
        PlanningRoom planningRoom = planningRoomService.getPlanningRoom(roomName);
        User user = userService.getUserByUsername(username);

        if (planningRoom == null || user == null) return ResponseEntity.badRequest().body("error while joining room");
        PlanningRoom joined = planningRoomService.joinPlanningRoom(username, roomName);
        System.out.println("joined"+ joined);
        if (joined == null) return ResponseEntity.badRequest().body("error while joining room");
        return ResponseEntity.ok("room joined successfully");
    }
}
