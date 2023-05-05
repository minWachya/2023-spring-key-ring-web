package com.example.keyringserver.service;


import com.example.keyringserver.model.KeyRingEntity;
import com.example.keyringserver.persistence.KeyRingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class KeyRingService {
    @Autowired
    private KeyRingRepository repository;

    // 생성
    public List<KeyRingEntity> create(final KeyRingEntity entity) {
        validate(entity);           // 엔티티 유효성 검사
        repository.save(entity);    // DB에 저장
        return repository.findAll();
    }

    // 수정
    public List<KeyRingEntity> update(final KeyRingEntity entity) {
        validate(entity);   // 유효성 검사
        // entity가 Null이 아닌 경우 실행
        // 수정 요청한 KeyRing id와 같은 KeyRing 찾기
        List<KeyRingEntity> keyRingEntityList = new ArrayList<>();

        final Optional<KeyRingEntity> optional = repository.findById(entity.getId());
        optional.ifPresent(keyRing -> {
            // userId, title, detail, imgUrl 수정 후 저장
            keyRing.setUserId(entity.getUserId());
            keyRing.setTitle(entity.getTitle());
            keyRing.setDetail(entity.getDetail());
            keyRing.setImgUrl(entity.getImgUrl());

            keyRingEntityList.add(keyRing);

            repository.save(keyRing);
        });

        return keyRingEntityList;
    }

    // 검색
    public List<KeyRingEntity> search(final String title) {
        // 타이틀이 같은 데이터 모두 반환
        return repository.findByTitle(title);
    }

    // 삭제
    public List<KeyRingEntity> delete(final KeyRingEntity entity) {
        validate(entity);       // 유효성 검사

        try {
            // DB에서 데이터 삭제
            repository.deleteById(entity.getId());
        } catch (Exception e) {
            log.error("error deleting entity:" + entity.getUserId(), e);

            throw new RuntimeException("error deleting entity: " + entity.getUserId());
        }
        // 모든 키링 리스트 반환
        return repository.findAll();
    }

    // 모든 KeyRing List 리턴
    public List<KeyRingEntity> retrieve() {
        return repository.findAll();
    }

    // 엔티티 유효성 확인
    private static void validate(KeyRingEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
