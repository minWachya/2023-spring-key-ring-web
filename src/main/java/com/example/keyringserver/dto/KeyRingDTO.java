package com.example.keyringserver.dto;

import com.example.keyringserver.model.KeyRingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 키링 DTO
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KeyRingDTO {
    private String id;
    private String userId;
    private String title;
    private String detail;
    private String imgUrl;

    public KeyRingDTO(final KeyRingEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.title = entity.getTitle();
        this.detail = entity.getDetail();
        this.imgUrl = entity.getImgUrl();
    }
    public static KeyRingEntity toEntity(final KeyRingDTO dto) {
        return KeyRingEntity.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .detail(dto.getDetail())
                .imgUrl(dto.getImgUrl())
                .build();
    }
}