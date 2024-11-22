package com.example.metapor.Domain.user.service;

import com.example.metapor.Domain.user.dto.TokenDto;
import com.example.metapor.Domain.user.dto.UserRegisterRequestDto;
import com.example.metapor.Domain.user.entity.User;
import com.example.metapor.Domain.user.entity.dao.UserRepository;
import com.example.metapor.common.auth.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public TokenDto register(UserRegisterRequestDto requestDto) {

        if (userRepository.existsById(requestDto.id())) {
            throw new IllegalArgumentException("이미 가입된 아이디입니다.");
        }

        User user = User.builder()
                .id(requestDto.id())
                .pw(passwordEncoder.encode(requestDto.password()))
                .isDoctor(requestDto.isDoctor())
                .build();

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        return new TokenDto(
                accessToken,
                refreshToken
        );
    }
}
