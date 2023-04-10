package com.example.keyringserver.dto;

import com.example.keyringserver.model.KeyRingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KeyRingDTO {
    private String id;
    private String title;
    private String detail;
    private String imgUrl;

    public KeyRingDTO(final KeyRingEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.detail = entity.getDetail();
        this.imgUrl = entity.getImgUrl();
    }

    // Controller가 받은 KeyRingDTO KeyRingEntity 변환 필요
    public static KeyRingEntity toEntity(final KeyRingDTO dto) {
        return KeyRingEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .detail(dto.getDetail())
                .imgUrl(dto.getImgUrl())
                .build();
    }
}