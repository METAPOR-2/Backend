package com.example.metapor.Domain.user.dto;

import com.example.metapor.Domain.user.entity.ClinicType;
import com.example.metapor.Domain.user.entity.ClinicTypeDoctorMapping;

import java.util.List;

public record ClinicTypeResponseDto(
        Long clinicTypeId,
        String title,
        String description,
        int lowPrice,
        int highPrice
) {
    public static ClinicTypeResponseDto from(ClinicTypeDoctorMapping clinicType) {
        return new ClinicTypeResponseDto(
                clinicType.getId(),
                clinicType.getType().getTitle(),
                clinicType.getDescription(),
                clinicType.getHighPrice(),
                clinicType.getLowPrice()
        );
    }

    public static List<ClinicTypeResponseDto> from(List<ClinicTypeDoctorMapping> clinicTypes) {
        return clinicTypes.stream()
                .map(ClinicTypeResponseDto::from).toList();
    }


}
