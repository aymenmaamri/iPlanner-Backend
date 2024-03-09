package hackathon.backend.iplanner.dto;

import hackathon.backend.iplanner.model.UserEvents;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanningRoomDto {
    @NotEmpty
    private String roomName;
    private String roomOwner;
    private String roomLeader;
    private int maxRoomPlannersNumber;

    private Map<String, UserEvents> userEvents;
}
