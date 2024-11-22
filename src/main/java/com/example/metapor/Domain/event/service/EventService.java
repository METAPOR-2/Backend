package com.example.metapor.Domain.event.service;

import com.example.metapor.Domain.event.dto.CreateEventRequestDto;
import com.example.metapor.Domain.event.entity.Event;
import com.example.metapor.Domain.event.entity.dao.EventRepository;
import com.example.metapor.Domain.user.entity.Doctor;
import com.example.metapor.Domain.user.entity.User;
import com.example.metapor.Domain.user.entity.dao.DoctorRepository;
import com.example.metapor.Domain.user.entity.dao.UserRepository;
import com.example.metapor.common.auth.JwtUtils;
import com.example.metapor.common.dto.JustIdDto;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final JwtUtils jwtUtils;
    private final DoctorRepository doctorRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public JustIdDto createEvent(String authToken, CreateEventRequestDto requestDto) throws CustomException {
        System.out.println("authToken = " + authToken);
        User user = userRepository.findById(jwtUtils.getUserIdFromToken(authToken)).orElseThrow(() -> CustomException.of(ErrorCode.USER_NOT_FOUND));
        Doctor doctor = doctorRepository.findById(requestDto.doctorId())
                .orElseThrow(() -> CustomException.of(ErrorCode.DOCTOR_NOT_FOUND));
        Event event = Event.builder()
                .numberOfApplicants(requestDto.patientNumber())
                .address(requestDto.location())
                .comment(requestDto.comment())
                .patient(user.getPatient())
                .applicationDateTime(requestDto.dateTime())
                .doctor(doctor)
                .build();

        eventRepository.save(event);

        return new JustIdDto(event.getId());
    }
}
