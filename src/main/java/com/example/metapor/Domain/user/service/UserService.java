package com.example.metapor.Domain.user.service;

import com.example.metapor.Domain.user.dto.DoctorInfoRequestDto;
import com.example.metapor.Domain.user.dto.PatientInfoRequestDto;
import com.example.metapor.Domain.user.dto.TokenDto;
import com.example.metapor.Domain.user.dto.UserRegisterRequestDto;
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
                .build());
        return SimpleResponse.success();
    }

    @Transactional
    public SimpleResponse addDocterInfo(String token, DoctorInfoRequestDto requestDto) throws CustomException {
        // 토큰에서 doctorId 추출
        String userId = jwtUtils.getUserIdFromToken(token);

        // 의사 정보 조회 (없으면 예외 발생)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.of(ErrorCode.USER_NOT_FOUND));
        Doctor doctor = user.getDoctor();
        // 의사 정보 업데이트
        doctor.getUser().setName(requestDto.name());
        doctor.getUser().setPhone(requestDto.phone());
        doctor.setType(requestDto.doctorType());
        doctor.setHospitalName(requestDto.hospitalName());
        doctor.setRegNumber(requestDto.regNumber());

        // 자격증 이미지 설정 (이미지 처리는 별도로 해야 할 수 있음)


        // 의사 정보 저장
        doctorRepository.save(doctor);

        // 위치 정보 저장
        locationRepository.save(Location.builder()
                .user(doctor.getUser()) // Location에 doctor 연결
                .address(requestDto.location().address())
                .si(requestDto.location().si())
                .gu(requestDto.location().gu())
                .doro(requestDto.location().doro())
                .build());

        return SimpleResponse.success();
    }



}
