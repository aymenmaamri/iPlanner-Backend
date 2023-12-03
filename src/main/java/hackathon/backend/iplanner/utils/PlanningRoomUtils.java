package hackathon.backend.iplanner.utils;

import hackathon.backend.iplanner.model.PlayerPositionOnBoard;

import java.util.HashMap;
import java.util.Map;

import static hackathon.backend.iplanner.model.PlanningRoom.MAX_PLAYERS_PER_ROOM;

public class PlanningRoomUtils {

    // Initialize playerPositions with 7 entries
    public static Map<String, PlayerPositionOnBoard> initializePlayerPositions() {
        Map<String, PlayerPositionOnBoard> positionsMap = new HashMap<>();

        for (int i = 1; i <= MAX_PLAYERS_PER_ROOM; i++) {
            String positionKey = "pos" + i;
            positionsMap.put(positionKey, new PlayerPositionOnBoard("", false, positionKey));
        }

        return positionsMap;
    }

}
