package hackathon.backend.iplanner.dto;

import hackathon.backend.iplanner.model.UserEvent;
import hackathon.backend.iplanner.model.UserStory;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanningRoomDto {
    @NotEmpty
    private String roomName;
    private String roomOwner;
    private int maxRoomPlannersNumber;

    private Map<String, UserEvent> userEvents;
}
