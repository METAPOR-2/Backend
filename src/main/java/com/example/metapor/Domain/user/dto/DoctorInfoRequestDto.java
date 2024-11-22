package com.example.metapor.Domain.user.dto;

import org.springframework.web.multipart.MultipartFile;

public record DoctorInfoRequestDto(
    String name,
    String phone,
    String doctorType,
    String hospitalName,
    String regNumber,
    LocationDto location
) {
}
