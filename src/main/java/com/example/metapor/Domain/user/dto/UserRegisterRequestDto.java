package com.example.metapor.Domain.user.dto;

public record UserRegisterRequestDto(
    String id,
    String password,
    boolean isDoctor
) {
}
