package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.StoryStatus;
import hackathon.backend.iplanner.model.PokerCard;
import lombok.*;

import java.util.HashMap;
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
        private int points;
        private HashMap<String, PokerCard> playerEstimations;
    }

    private EstimateStoryPayload payload;
}
