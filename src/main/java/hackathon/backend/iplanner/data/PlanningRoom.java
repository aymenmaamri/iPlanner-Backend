package hackathon.backend.iplanner.data;

import java.util.ArrayList;
import java.util.UUID;

public class PlanningRoom {

    public String roomId;
    public String roomName;
    public String roomOwnerId;

    public String roomOwnerUsername;
    public ArrayList<String> users;

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "PlanningRoom{" +
                "roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                '}';
    }

    public PlanningRoom(String roomName, String userId, String username) {
        this.roomName = roomName;
        this.roomId = UUID.randomUUID().toString();
        this.users = new ArrayList<>();
        this.roomOwnerId = userId;
        this.roomOwnerUsername = username;
    }


    boolean isUserRoomOwner(String userId){
        return this.roomOwnerId.equals(userId);
    }

    public String addUserToRoom(String username){
        boolean exists = users.contains(username);
        if (exists) return null;
        this.users.add(username);
        return username;
    }

    public boolean isUserInRoom(String username){
        return this.users.contains(username);
    }

}
