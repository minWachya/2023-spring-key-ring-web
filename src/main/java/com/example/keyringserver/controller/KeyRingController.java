package com.example.keyringserver.controller;


import com.example.keyringserver.dto.KeyRingDTO;
import com.example.keyringserver.dto.ResponseDTO;
import com.example.keyringserver.model.KeyRingEntity;
import com.example.keyringserver.service.KeyRingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("KeyRing")
public class KeyRingController {
    // 자동 주입
    @Autowired
    private KeyRingService service;

    @PostMapping
    public ResponseEntity<?> createKeyRing(@RequestBody KeyRingDTO dto) {
        try {
            // dto로 entity 생성
            KeyRingEntity entity = KeyRingDTO.toEntity(dto);
            // entity 저장 후 userid가 생성한 KeyRing entity 목록 받아옴
            List<KeyRingEntity> entities = service.create(entity);
            // KeyRing entity 목록을 KeyRing dto 목록으로 변경
            List<KeyRingDTO> dtos = entities.stream().map(KeyRingDTO::new).collect(Collectors.toList());
            // response 만들어서 출력
            ResponseDTO<KeyRingDTO> response = ResponseDTO.<KeyRingDTO>builder()
                    .data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<KeyRingDTO> response = ResponseDTO.<KeyRingDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateKeyRing(@RequestBody KeyRingDTO dto) {
        // dto를 entity로 변경
        KeyRingEntity entity = KeyRingDTO.toEntity(dto);
        // entity 수정 후 userid가 생성한 KeyRing entity 목록 받아옴
        List<KeyRingEntity> entities = service.update(entity);
        // KeyRing entity 목록을 KeyRing dto 목록으로 변경
        List<KeyRingDTO> dtos = entities.stream().map(KeyRingDTO::new).collect(Collectors.toList());
        // response 만들어서 출력
        ResponseDTO<KeyRingDTO> response = ResponseDTO.<KeyRingDTO>builder()
                .data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteKeyRing(@RequestBody KeyRingDTO dto) {
        try {
            // dto를 entity로 변경
            KeyRingEntity entity = KeyRingDTO.toEntity(dto);
            // entity 삭제 후 userid가 생성한 KeyRing entity 목록 받아옴
            List<KeyRingEntity> entities = service.delete(entity);
            // KeyRing entity 목록을 KeyRing dto 목록으로 변경
            List<KeyRingDTO> dtos = entities.stream().map(KeyRingDTO::new).collect(Collectors.toList());
            // response 만들어서 출력
            ResponseDTO<KeyRingDTO> response = ResponseDTO.<KeyRingDTO>builder()
                    .data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<KeyRingDTO> response = ResponseDTO.<KeyRingDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> retrieveKeyRingList(@RequestParam(required = false) String userId) {
        // userid가 생성한 KeyRing entity 목록 받아옴
        List<KeyRingEntity> entities = service.retrieve(userId);
        // KeyRing entity 목록을 KeyRing dto 목록으로 변경
        List<KeyRingDTO> dtos = entities.stream().map(KeyRingDTO::new).collect(Collectors.toList());
        // response 만들어서 출력
        ResponseDTO<KeyRingDTO> response = ResponseDTO.<KeyRingDTO>builder()
                .data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
}