package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.EventType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStoryEvent extends RoomEvent{

   //TODO: is using a static inner class to simply create the type for the CreateStoryPayload
   // a valid approach in this use case?
    @Getter
    @Setter
    public static class CreateStoryPayload {
        private String title;
        private String description;
    }

    //private CreateStoryPayload payload;

    private String title;
    private String description;
    private final EventType type = EventType.CREATE_STORY;

}
