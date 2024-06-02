package momentum.back.userapi.dto;

import java.time.LocalDate;

public record RegisterRequestDTO(String login, String email, String name, LocalDate birthDate, String password) {
}
