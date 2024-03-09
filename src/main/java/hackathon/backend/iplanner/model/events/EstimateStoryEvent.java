package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.StoryStatus;
import hackathon.backend.iplanner.model.PokerCard;
import hackathon.backend.iplanner.model.UserStory;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimateStoryEvent extends RoomEvent {
    @Getter
    @Setter
    public static class EstimateStoryPayload {
        // todo: consider using IDs as an identifier
        private String title;
        private String description;

        private HashMap<String, PokerCard> playerEstimations;
        private int estimate;
    }

    private EstimateStoryPayload payload;
    private List<UserStory> estimatedRoomStories;
}
