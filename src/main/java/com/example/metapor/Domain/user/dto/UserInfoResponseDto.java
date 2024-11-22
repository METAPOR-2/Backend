package com.example.metapor.Domain.user.dto;

import com.example.metapor.Domain.user.entity.User;

public record UserInfoResponseDto(
        String id,
        String name,
        String phone,
        boolean isDoctor
) {
    public static UserInfoResponseDto from(User user) {
        return new UserInfoResponseDto(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.isDoctor()
        );
    }
}
