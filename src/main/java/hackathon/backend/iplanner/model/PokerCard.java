package hackathon.backend.iplanner.model;


import hackathon.backend.iplanner.enums.PokerCardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PokerCard {
    @Id
    private ObjectId cardId;
    private int value;
    private String color;
    private PokerCardType type;
}
