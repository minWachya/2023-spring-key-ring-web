package com.example.keyringserver.controller;
import com.example.keyringserver.dto.ResponseDTO;
import com.example.keyringserver.dto.UserDTO;
import com.example.keyringserver.model.UserEntity;
import com.example.keyringserver.security.TokenProvider;
import com.example.keyringserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            // 요청받은 사용자 정보 DTO를 토대로 UserEntity 생성
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .id(userDTO.getId())
                    .username(userDTO.getUsername())
                    .build();
            // 생성한 UserEntity를 서버에 저장후 저장한 UserEntity 받아옴
            UserEntity registerUser = userService.create(user);
            // 받아온 User 정보를 DTO로 변환
            UserDTO responseUserDTO = UserDTO.builder()
                    .email(registerUser.getEmail())
                    .id(registerUser.getId())
                    .username(registerUser.getUsername())
                    .build();
            // 변환한 DTO 반환
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        // 요청받은 사용자 정보가 서버에 있는지 확인 후 반환
        UserEntity user = userService.getByCredentials(
                userDTO.getEmail(),
                userDTO.getPassword()
        );

        // 사용자 정보가 있으면
        if(user != null) {
            // 토큰 생성
            final String token = tokenProvider.create(user);
            // userDTO 만들어서 반환
            final UserDTO responseUserDTO = UserDTO.builder()
                    .token(token)
                    .email(user.getEmail())
                    .id(user.getId())
                    .username(user.getUsername())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}

