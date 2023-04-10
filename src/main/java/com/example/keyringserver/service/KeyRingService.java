package com.example.keyringserver.service;


import com.example.keyringserver.model.KeyRingEntity;
import com.example.keyringserver.persistence.KeyRingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j  // 로깅 레벨 관리해주는 라이브러리(Simple Logging Facade for Java)
@Service
public class KeyRingService {
    // KeyRingRepository 인터페이스 구현한 클래스를 JPA가 자동 생성, 자동 주입
    @Autowired
    private KeyRingRepository repository;

    public List<KeyRingEntity> create(final KeyRingEntity entity) {
        validate(entity);

        repository.save(entity);
        log.info("mmm Entity id: {} is saved", entity.getUserId());

        return repository.findAll();
    }

    // 수정 후 모든 KeyRing List 반환
    public List<KeyRingEntity> update(final KeyRingEntity entity) {
        validate(entity);

        // entity가 Null이 아닌 경우 실행
        // put 요청한 KeyRing id와 같은 KeyRing 찾기
        final Optional<KeyRingEntity> optional = repository.findById(entity.getId());
        optional.ifPresent(keyRing -> {
            // userId, title, detail, imgUrl 수정 후 저장
            keyRing.setUserId(entity.getUserId());
            keyRing.setTitle(entity.getTitle());
            keyRing.setDetail(entity.getDetail());
            keyRing.setImgUrl(entity.getImgUrl());

            repository.save(keyRing);
        });

        return retrieve(entity.getUserId());
    }

    // 삭제 후 모든 TodoE List 반환
    public List<KeyRingEntity> delete(final KeyRingEntity entity) {
        validate(entity);

        log.error("mmm:" + entity.toString());

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity:" + entity.getUserId(), e);

            throw new RuntimeException("error deleting entity: " + entity.getUserId());
        }
        return retrieve(entity.getUserId());
    }

    // userId가 생성한 모든 KeyRing List 리턴
    public List<KeyRingEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
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
