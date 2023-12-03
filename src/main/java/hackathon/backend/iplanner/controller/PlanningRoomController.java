package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.dto.PlanningRoomDto;
import hackathon.backend.iplanner.model.User;
import hackathon.backend.iplanner.service.PlanningRoomService;
import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
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
    public ResponseEntity<PlanningRoomDto> createPlanningRoom(@Valid @RequestBody PlanningRoomDto planningRoomDto){
        PlanningRoom planningRoom = planningRoomService.getPlanningRoom(planningRoomDto.getRoomName());
        if(planningRoom != null) return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String initiatorUsername = authentication.getName();
        // Call the service to create the planning room
        PlanningRoom created = planningRoomService.createPlanningRoom(planningRoomDto, initiatorUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(created, PlanningRoomDto.class));
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
    public ResponseEntity<PlanningRoomDto> joinPlanningRoom(@RequestParam String roomName, @RequestParam String username){
        PlanningRoom planningRoom = planningRoomService.getPlanningRoom(roomName);
        User user = userService.getUserByUsername(username);

        if (planningRoom == null || user == null) return ResponseEntity.badRequest().body(null);
        PlanningRoom joined = planningRoomService.joinPlanningRoom(username, roomName);
        return ResponseEntity.ok(modelMapper.map(joined, PlanningRoomDto.class));
    }

    @DeleteMapping("/planning-room/{roomName}")
    public ResponseEntity<String> deletePlanningRoom(@PathVariable String roomName){
        // get delete initiator
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String initiatorUsername = authentication.getName();
        try {
            planningRoomService.deletePlanningRoom(roomName, initiatorUsername);
            return ResponseEntity.status(204).body("");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/planning-room/isOwner")
    public ResponseEntity<Boolean> isUserPlanningRoomOwner(@RequestParam String roomName, @RequestParam String username){
        boolean isOwner = planningRoomService.isUserRoomOwner(roomName, username);
        return ResponseEntity.ok().body(isOwner);
    }
}
