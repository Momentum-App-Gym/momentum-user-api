package momentum.back.userapi.controllers;

import lombok.RequiredArgsConstructor;
import momentum.back.userapi.domain.user.User;
import momentum.back.userapi.dto.LoginRequestDTO;
import momentum.back.userapi.dto.RegisterRequestDTO;
import momentum.back.userapi.dto.ResponseDTO;
import momentum.back.userapi.infra.security.TokenService;
import momentum.back.userapi.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByLogin(body.login()).orElseThrow(() -> new RuntimeException("User not Found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getLogin(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByLogin(body.login());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            newUser.setBirthDate(body.birthDate());
            newUser.setLogin(body.login());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getLogin(), token));
        }
        return ResponseEntity.badRequest().build();
    }

}
