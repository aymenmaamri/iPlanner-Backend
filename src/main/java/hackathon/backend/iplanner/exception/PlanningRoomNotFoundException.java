package hackathon.backend.iplanner.exception;

public class PlanningRoomNotFoundException extends RuntimeException {

    public PlanningRoomNotFoundException(String roomName) {
        super("could not find planning room for the name : " + roomName);
    }

    public PlanningRoomNotFoundException() {
        super("could not find planning room");
    }
}