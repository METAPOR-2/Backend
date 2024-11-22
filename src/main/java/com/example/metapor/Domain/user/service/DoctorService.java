package com.example.metapor.Domain.user.service;

import com.example.metapor.Domain.user.dto.ClinicTypeRequestDto;
import com.example.metapor.Domain.user.dto.DoctorListResponseDto;
import com.example.metapor.Domain.user.dto.GetDoctorInfoResponseDto;
import com.example.metapor.Domain.user.entity.ClinicType;
import com.example.metapor.Domain.user.entity.ClinicTypeDoctorMapping;
import com.example.metapor.Domain.user.entity.Doctor;
import com.example.metapor.Domain.user.entity.User;
import com.example.metapor.Domain.user.entity.dao.ClinicTypeDoctorMappingRepository;
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

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final ClinicTypeRepository clinicTypeRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final DoctorRepository doctorRepository;
    private final ClinicTypeDoctorMappingRepository clinicTypeDoctorMappingRepository;


    public SimpleResponse addClinicType(String authToken, ClinicTypeRequestDto requestDto) throws CustomException {
        User user = userRepository.findById(jwtUtils.getUserIdFromToken(authToken)).orElseThrow(() -> CustomException.of(ErrorCode.USER_NOT_FOUND));
        ClinicType clinicType;
        if (!clinicTypeRepository.existsByTitle(requestDto.title())) {
            clinicType = clinicTypeRepository.save(new ClinicType(requestDto.title()));
        } else {
            clinicType = clinicTypeRepository.findByTitle(requestDto.title());
        }
        clinicTypeDoctorMappingRepository.save(
                new ClinicTypeDoctorMapping(
                        clinicType,
                        requestDto.lowPrice(),
                        requestDto.highPrice(),
                        requestDto.description(),
                        user.getDoctor()

                )
        );
        return SimpleResponse.success();
    }


    public RestResponse<GetDoctorInfoResponseDto> getDoctorInfo(String authToken, Long doctorId) throws CustomException {
        jwtUtils.getUserIdFromToken(authToken);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> CustomException.of(ErrorCode.DOCTOR_NOT_FOUND));
        return RestResponse.ok(GetDoctorInfoResponseDto.from(doctor));
    }

    public List<DoctorListResponseDto> getDoctorList(String authToken, String query, Integer number, LocalDate date) throws CustomException {
        jwtUtils.getUserIdFromToken(authToken);
        List<Doctor> doctors;
        if (query == null) {

            doctors = doctorRepository.findByOrderByRatingDesc();

        } else {
            if (number != null && date != null) {
                doctors = doctorRepository.findByQueryNumberDate(query, number, date);
            } else {
                doctors = doctorRepository.findByQuery(query);
            }
        }
        return DoctorListResponseDto.from(doctors);
    }
}
