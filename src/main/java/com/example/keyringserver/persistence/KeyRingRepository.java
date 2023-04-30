package com.example.keyringserver.persistence;

import com.example.keyringserver.model.KeyRingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 이거 생성하는 것만으로 DB 연결 끝!
// JpaRepository가 findById, save 등 기본 메서드 제공해줌
@Repository     // 예도 @Component 어노테이션 포함하므로 Bean임
public interface KeyRingRepository extends JpaRepository<KeyRingEntity, String> {
    // JpaRepository<테이블에 매핑될 엔터티 클래스, 기본 키 타입>

    // Jpa가 자동으로 쿼리 만들어줌
    List<KeyRingEntity> findByTitle(String title);
}