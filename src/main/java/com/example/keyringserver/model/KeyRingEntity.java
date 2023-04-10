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

@Builder
@NoArgsConstructor  // 롬복: 인자없는 생성자 생성
@AllArgsConstructor
@Data               // 롬복: getter/setter 추가
@Entity             // 엔티티 클래스 지정
@Table(name = "KeyRing")   //  테이블 이름 지정(미지정 시 기본 이름은 KeyRingEntity, KeyRing 테이블과 매핑)
public class KeyRingEntity {
    @Id            // 기본 키 지정
    @GeneratedValue(generator = "system-uuid")   // system-uuid라는 이름의 generator 이용하여 uuid 자동 생성
    @GenericGenerator(name="system-uuid", strategy = "uuid")    // system-uuid라는 이름의 generator를 생성함. 얘는 uuid라는 문자열 이용해 id 생성함.
    private String id;
    private String userId;
    private String title;
    private String detail;
    private String imgUrl;
}