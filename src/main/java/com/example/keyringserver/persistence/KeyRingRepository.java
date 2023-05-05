package com.example.keyringserver.persistence;

import com.example.keyringserver.model.KeyRingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyRingRepository extends JpaRepository<KeyRingEntity, String> {

    // title 검색 결과 반환
    List<KeyRingEntity> findByTitle(String title);
}