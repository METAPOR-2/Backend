package com.example.metapor.Domain.user.dto;

import com.example.metapor.Domain.user.entity.Doctor;

import java.util.List;

public record GetDoctorInfoResponseDto(
        String doctorId,
        String name,
        String address,
        String phoneNumber,
        String profileImage,
        String hospitalName,
        String ableTime,
        Float rating,
        Integer reviewCount,
        List<ClinicTypeResponseDto> clinicTypes
) {
    public static GetDoctorInfoResponseDto from(Doctor doctor) {
        return new GetDoctorInfoResponseDto(
                doctor.getId().toString(),
                doctor.getUser().getName(),
                doctor.getUser().getLocation().getAddress(),
                doctor.getUser().getPhone(),
                null,
                doctor.getHospitalName(),
                doctor.getAbleTime(),
                doctor.getRating(),
                doctor.getReviewCount(),
                ClinicTypeResponseDto.from(doctor.getClinicTypeMappings())
        );
    }
}
