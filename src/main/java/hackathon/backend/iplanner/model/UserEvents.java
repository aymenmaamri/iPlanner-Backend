package hackathon.backend.iplanner.model;

import hackathon.backend.iplanner.model.events.RoomEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEvents {
    // Do I need an id here?
    @Id
    private String id;
    private User user;
    private List<RoomEvent> eventList;
}
