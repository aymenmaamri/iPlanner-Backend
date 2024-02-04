package hackathon.backend.iplanner.model;

import hackathon.backend.iplanner.enums.StoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStory {
    @Id
    private ObjectId storyId;
    private String title;
    private String description;
    private Map<String, PokerCard> playerEstimations;
    // Todo: maybe implement later
    // private StoryStatus status;
    private int estimate;
}
