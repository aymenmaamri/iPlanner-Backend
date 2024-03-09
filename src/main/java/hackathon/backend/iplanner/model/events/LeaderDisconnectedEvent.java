package hackathon.backend.iplanner.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaderDisconnectedEvent extends RoomEvent{
  private String disconnectedLeader;
  private String newLeader;
}
