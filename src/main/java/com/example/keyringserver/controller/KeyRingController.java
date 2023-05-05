package com.example.keyringserver.controller;


import com.example.keyringserver.dto.KeyRingDTO;
import com.example.keyringserver.dto.KeyRingSearchRequestBodyDTO;
import com.example.keyringserver.dto.ResponseDTO;
import com.example.keyringserver.model.KeyRingEntity;
import com.example.keyringserver.service.KeyRingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("keyring")
public class KeyRingController {
    // 서비스 자동 주입
    @Autowired
    private KeyRingService service;

    // 생성
    @PostMapping
    public ResponseEntity<?> createKeyRing(@RequestBody KeyRingDTO dto) {
        try {
            // dto로 entity 생성
            KeyRingEntity entity = KeyRingDTO.toEntity(dto);
            // entity 저장 후 모든 KeyRing entity 목록 받아옴
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

    // 수정
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

    // 검색
    @PostMapping("/search")
    public ResponseEntity<?> searchKeyRing(@RequestBody(required = false) KeyRingSearchRequestBodyDTO request) {
        // title이 같은 KeyRing entity 목록 받아옴
        List<KeyRingEntity> entities = service.search(request.getTitle());
        // KeyRing entity 목록을 KeyRing dto 목록으로 변경
        List<KeyRingDTO> dtos = entities.stream().map(KeyRingDTO::new).collect(Collectors.toList());
        // response 만들어서 출력
        ResponseDTO<KeyRingDTO> response = ResponseDTO.<KeyRingDTO>builder()
                .data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    // 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteKeyRing(@RequestBody KeyRingDTO dto) {
        try {
            // dto를 entity로 변경
            KeyRingEntity entity = KeyRingDTO.toEntity(dto);
            // entity 삭제 후 userId가 생성한 KeyRing entity 목록 받아옴
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

    // 모든 KeyRing List 리턴
    @GetMapping
    public ResponseEntity<?> retrieveKeyRingList() {
        // userid가 생성한 KeyRing entity 목록 받아옴
        List<KeyRingEntity> entities = service.retrieve();
        // KeyRing entity 목록을 KeyRing dto 목록으로 변경
        List<KeyRingDTO> dtos = entities.stream().map(KeyRingDTO::new).collect(Collectors.toList());
        // response 만들어서 출력
        ResponseDTO<KeyRingDTO> response = ResponseDTO.<KeyRingDTO>builder()
                .data(dtos).build();
        return ResponseEntity.ok().body(response);
    }
}