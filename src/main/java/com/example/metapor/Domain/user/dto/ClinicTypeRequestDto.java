package com.example.metapor.Domain.user.dto;

public record ClinicTypeRequestDto(
        String title,
        int price,
        String description
) {
}
