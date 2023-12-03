package hackathon.backend.iplanner.dto;

import hackathon.backend.iplanner.model.Story;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanningRoomDto {
    @NotEmpty
    private String roomName;
    private String roomOwner;
    private List<String> joinedUsers;
    private int numberOfPlanners;

    private List<Story> planningStories;

}
