package com.example.metapor.Domain.user.dto;

import com.example.metapor.Domain.user.entity.Doctor;

import java.util.List;

public record GetDoctorInfoResponseDto(
        String doctorId,
        String name,
        String address,
        String profileImage,
        String hospitalName,
        List<ClinicTypeResponseDto> clinicTypes
) {
    public static GetDoctorInfoResponseDto from(Doctor doctor) {
        return new GetDoctorInfoResponseDto(
                doctor.getId().toString(),
                doctor.getUser().getName(),
                doctor.getUser().getLocation().getAddress(),
                null,
                doctor.getHospitalName(),
                ClinicTypeResponseDto.from(doctor.getClinicTypes())
        );
    }
}
