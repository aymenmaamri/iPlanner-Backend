package hackathon.backend.iplanner.service;

import hackathon.backend.iplanner.dto.PlanningRoomDto;
import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.User;
import hackathon.backend.iplanner.repository.PlanningRoomRepository;
import hackathon.backend.iplanner.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlanningRoomService {
    private final PlanningRoomRepository planningRoomRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    PlanningRoomService(PlanningRoomRepository planningRoomRepository, UserService userService, ModelMapper modelMapper){
        this.planningRoomRepository = planningRoomRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public PlanningRoom getPlanningRoom(String roomName){
        Optional<PlanningRoom> planningRoom = planningRoomRepository.findByRoomName(roomName);
        if (planningRoom.isPresent()) return planningRoom.get();
        return null;
    }


    public void createPlanningRoom(PlanningRoomDto planningRoomDto){
        PlanningRoom planningRoom = modelMapper.map(planningRoomDto, PlanningRoom.class);
        // TODO: maybe extract the creation of objects logic from here
        planningRoom.setCreationTime(new Date());
        planningRoom.setJoinedUsers(new ArrayList<String>());
        // TODO: add room owner, should i do this with using the request initiator's token?
        planningRoomRepository.save(planningRoom);
    }

    public List<PlanningRoom> getPlanningRooms(){
        return planningRoomRepository.findAll();
    }


    // TODO: complete join room logic
    /*public PlanningRoom joinPlanningRoom(String username, String roomId){
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
    }*/
}
