package hackathon.backend.iplanner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @NotEmpty
    private String username;
    @Email
    private String email;
    @NotEmpty
    private String password;
}
