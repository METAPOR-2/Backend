package com.example.metapor.Domain.user.service;

import com.example.metapor.Domain.user.dto.ClinicTypeRequestDto;
import com.example.metapor.Domain.user.dto.DoctorListResponseDto;
import com.example.metapor.Domain.user.dto.GetDoctorInfoResponseDto;
import com.example.metapor.Domain.user.dto.IntrohospitalRequestDto;
import com.example.metapor.Domain.user.entity.ClinicType;
import com.example.metapor.Domain.user.entity.Doctor;
import com.example.metapor.Domain.user.entity.User;
import com.example.metapor.Domain.user.entity.dao.ClinicTypeRepository;
import com.example.metapor.Domain.user.entity.dao.DoctorRepository;
import com.example.metapor.Domain.user.entity.dao.UserRepository;
import com.example.metapor.common.auth.JwtUtils;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.exception.ErrorCode;
import com.example.metapor.common.response.RestResponse;
import com.example.metapor.common.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final ClinicTypeRepository clinicTypeRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final DoctorRepository doctorRepository;


    public SimpleResponse addClinicType(String authToken, ClinicTypeRequestDto requestDto) throws CustomException {
        User user = userRepository.findById(jwtUtils.getUserIdFromToken(authToken)).orElseThrow(() -> CustomException.of(ErrorCode.USER_NOT_FOUND));
        clinicTypeRepository.save(ClinicType.fromDto(requestDto, user.getDoctor()));
        return SimpleResponse.success();
    }


    public RestResponse<GetDoctorInfoResponseDto> getDoctorInfo(String authToken, Long doctorId) throws CustomException {
        jwtUtils.getUserIdFromToken(authToken);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> CustomException.of(ErrorCode.DOCTOR_NOT_FOUND));
        return RestResponse.ok(GetDoctorInfoResponseDto.from(doctor));
    }

    public List<DoctorListResponseDto> getDoctorList(String authToken) throws CustomException {
        jwtUtils.getUserIdFromToken(authToken);
        List<Doctor> doctors = doctorRepository.findAll();
        return DoctorListResponseDto.from(doctors);
    }

    public RestResponse<IntrohospitalRequestDto> getHospitalInfo(String authToken, Long doctorId) throws CustomException {
        // 인증 토큰에서 사용자 ID를 추출
        jwtUtils.getUserIdFromToken(authToken);
        // 병원 ID로 병원 정보를 찾음
        // 의사 ID로 의사 정보 조회
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> CustomException.of(ErrorCode.DOCTOR_NOT_FOUND));
        // 의사 정보를 DTO로 변환

        String introhospital = doctor.getIntrohospital(); // 병원 소개
        String career = doctor.getCareer(); // 병원 경력

        // 병원 정보 DTO 생성
        IntrohospitalRequestDto hospitalInfo = new IntrohospitalRequestDto(
                introhospital,
                career
        );
        // 응답 반환
        return RestResponse.ok(hospitalInfo);}

    public RestResponse<IntrohospitalRequestDto> updateHospitalInfo(String authToken, Long doctorId, IntrohospitalRequestDto updateRequest) throws CustomException {
        // 인증 토큰에서 사용자 ID를 추출
        jwtUtils.getUserIdFromToken(authToken);

        // 의사 ID로 의사 정보 조회
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> CustomException.of(ErrorCode.DOCTOR_NOT_FOUND));

        // 의사 정보 업데이트

        if (updateRequest.introhospital() != null) {
            doctor.setIntrohospital(updateRequest.introhospital());  // 병원 소개 수정
        }
        if (updateRequest.career() != null) {
            doctor.setCareer(updateRequest.career());  // 병원 경력 수정
        }
        // 수정된 정보를 데이터베이스에 저장
        doctorRepository.save(doctor);

        // 수정된 정보를 반환
        return RestResponse.ok(updateRequest);
    }
}
