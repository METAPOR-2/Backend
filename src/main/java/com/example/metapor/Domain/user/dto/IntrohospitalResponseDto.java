package com.example.metapor.Domain.user.dto;

import com.example.metapor.Domain.user.entity.Doctor;
import org.springframework.web.multipart.MultipartFile;

public record IntrohospitalResponseDto (
        MultipartFile img,
        String introhospital,
        String career
){

}
