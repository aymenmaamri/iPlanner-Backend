package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.model.events.CreateStoryEvent;
import hackathon.backend.iplanner.service.PlanningRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planning-room/{roomName}/stories")
public class StoriesController {

    private final PlanningRoomService planningRoomService;

    public StoriesController(PlanningRoomService planningRoomService) {
        this.planningRoomService = planningRoomService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CreateStoryEvent>> getRoomStories(@PathVariable String roomName){
        List<CreateStoryEvent> stories = planningRoomService.getRoomStories(roomName);
        return ResponseEntity.ok(stories);
    }}
