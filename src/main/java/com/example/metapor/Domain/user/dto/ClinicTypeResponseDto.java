package com.example.metapor.Domain.user.dto;

import com.example.metapor.Domain.user.entity.ClinicType;

import java.util.List;

public record ClinicTypeResponseDto(
        Long clinicTypeId,
        String title,
        String description,
        int price
) {
    public static ClinicTypeResponseDto from(ClinicType clinicType) {
        return new ClinicTypeResponseDto(
                clinicType.getId(),
                clinicType.getType(),
                clinicType.getDescription(),
                clinicType.getPrice()
        );
    }

    public static List<ClinicTypeResponseDto> from(List<ClinicType> clinicTypes) {
        return clinicTypes.stream()
                .map(ClinicTypeResponseDto::from).toList();
    }
}
