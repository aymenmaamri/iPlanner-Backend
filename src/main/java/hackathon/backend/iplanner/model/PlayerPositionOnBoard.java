package hackathon.backend.iplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerPositionOnBoard {
    private String username;
    private boolean assigned;
    private String position;

    @Override
    public String toString() {
        return "PlayerPositionOnBoard{" +
                "username='" + username + '\'' +
                ", assigned=" + assigned +
                ", position='" + position + '\'' +
                '}';
    }
}
