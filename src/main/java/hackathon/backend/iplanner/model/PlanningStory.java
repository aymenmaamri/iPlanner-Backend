package hackathon.backend.iplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanningStory {
    @Id
    private ObjectId storyId;
    private String title;
    private String description;
    private Map<String, Integer> playerEstimations;
    private String status;
    private int finalEstimate;
}
