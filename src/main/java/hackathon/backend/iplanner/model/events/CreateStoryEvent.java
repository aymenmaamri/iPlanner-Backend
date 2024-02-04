package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.EventType;
import hackathon.backend.iplanner.enums.StoryStatus;
import hackathon.backend.iplanner.model.UserStory;
import lombok.*;

import java.util.List;

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
        private StoryStatus status;
    }
    private CreateStoryPayload payload;

}
