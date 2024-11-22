package com.example.metapor.Domain.user.dto;

import com.example.metapor.Domain.user.entity.Doctor;

import java.util.List;

public record DoctorListResponseDto(
        Long doctorId,
        String name,
        String profileImage,
        String hospitalName,
        Float rating,
        Integer reviewCount,
        List<ClinicTypeResponseDto> clinicTypes
) {
    public static DoctorListResponseDto from(Doctor doctor) {
        return new DoctorListResponseDto(
                doctor.getId(),
                doctor.getUser().getName(),
                null,
                doctor.getHospitalName(),
                doctor.getRating(),
                doctor.getReviewCount(),
                ClinicTypeResponseDto.from(doctor.getClinicTypeMappings())
        );
    }

    public static List<DoctorListResponseDto> from(List<Doctor> doctors) {
        return doctors.stream()
                .map(DoctorListResponseDto::from)
                .toList();
    }
}
