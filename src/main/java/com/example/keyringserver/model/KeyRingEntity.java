package com.example.keyringserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

// 키링 Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "KeyRing")
public class KeyRingEntity {
    @Id            // 기본 키 지정
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String userId;
    private String title;
    private String detail; // 제품 상제
    private String imgUrl;  // 제품 이미지 링크
}