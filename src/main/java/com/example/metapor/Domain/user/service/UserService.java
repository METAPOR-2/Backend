package com.example.metapor.Domain.user.service;

import com.example.metapor.Domain.user.dto.*;
import com.example.metapor.Domain.user.entity.Doctor;
import com.example.metapor.Domain.user.entity.Location;
import com.example.metapor.Domain.user.entity.Patient;
import com.example.metapor.Domain.user.entity.User;
import com.example.metapor.Domain.user.entity.dao.DoctorRepository;
import com.example.metapor.Domain.user.entity.dao.LocationRepository;
import com.example.metapor.Domain.user.entity.dao.PatientRepository;
import com.example.metapor.Domain.user.entity.dao.UserRepository;
import com.example.metapor.common.auth.JwtUtils;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.exception.ErrorCode;
import com.example.metapor.common.response.RestResponse;
import com.example.metapor.common.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final JwtUtils jwtUtils;

    public TokenDto register(UserRegisterRequestDto requestDto) throws CustomException {

        if (userRepository.existsById(requestDto.id())) {
            throw CustomException.of(ErrorCode.USER_ALREADY_EXIST);
        }

        User user = userRepository.save(User.builder()
                .id(requestDto.id())
                .pw(passwordEncoder.encode(requestDto.password()))
                .isDoctor(requestDto.isDoctor())
                .build());



        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        return new TokenDto(
                accessToken,
                refreshToken
        );
    }


    @Transactional
    public SimpleResponse addPatientInfo(String token, PatientInfoRequestDto requestDto) throws CustomException {
        String userId = jwtUtils.getUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(() -> CustomException.of(ErrorCode.USER_NOT_FOUND));

        user.setName(requestDto.name());
        user.setPhone(requestDto.phone());
        userRepository.save(user);
        patientRepository.save(
                Patient.builder()
                        .user(user)
                        .needs(String.join(",", requestDto.needs()))
                        .build()
        );


        locationRepository.save(Location.builder()
                .user(user)
                .address(requestDto.location().address())
                .si(requestDto.location().si())
                .gu(requestDto.location().gu())
                .doro(requestDto.location().doro())
                .locationRange(requestDto.location().range())
                .build());
        return SimpleResponse.success();
    }

    @Transactional
    public SimpleResponse addDoctorInfo(String token, DoctorInfoRequestDto requestDto) throws CustomException {
        // 토큰에서 doctorId 추출
        String userId = jwtUtils.getUserIdFromToken(token);

        // 의사 정보 조회 (없으면 예외 발생)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.of(ErrorCode.USER_NOT_FOUND));
        user.setName(requestDto.name());
        user.setPhone(requestDto.phone());
        userRepository.save(user);
        doctorRepository.save(
                Doctor.builder()
                        .type(requestDto.doctorType())
                        .hospitalName(requestDto.hospitalName())
                        .regNumber(requestDto.regNumber())
                        .user(user)
                        .build()
        );
        locationRepository.save(Location.builder()
                .user(user)
                .address(requestDto.location().address())
                .si(requestDto.location().si())
                .gu(requestDto.location().gu())
                .doro(requestDto.location().doro())
                .locationRange(requestDto.location().range())
                .build());

        return SimpleResponse.success();
    }


    public UserInfoResponseDto getMyInfo(String authToken) throws CustomException {
        String userId = jwtUtils.getUserIdFromToken(authToken);
        User user = userRepository.findById(userId).orElseThrow(() -> CustomException.of(ErrorCode.USER_NOT_FOUND));
        return UserInfoResponseDto.from(user);
    }
}
