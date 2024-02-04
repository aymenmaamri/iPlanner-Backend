package hackathon.backend.iplanner.exception;

public class PlanningRoomAlreadyExistsException extends RuntimeException{

    public PlanningRoomAlreadyExistsException() {
        super("Planning room already exists");
    }
}
