package com.example.metapor.Domain.event.service;

import com.example.metapor.Domain.event.dto.CreateEventRequestDto;
import com.example.metapor.Domain.event.dto.EventInfoDto;
import com.example.metapor.Domain.event.dto.GetEventPageResponseDto;
import com.example.metapor.Domain.event.dto.RejectReasonRequestDto;
import com.example.metapor.Domain.event.entity.ClinicTypeEventMapping;
import com.example.metapor.Domain.event.entity.dao.ClinicTypeEventMappingRepository;
import com.example.metapor.Domain.event.entity.Event;
import com.example.metapor.Domain.event.entity.dao.EventRepository;
import com.example.metapor.Domain.user.entity.ClinicTypeDoctorMapping;
import com.example.metapor.Domain.user.entity.Doctor;
import com.example.metapor.Domain.user.entity.User;
import com.example.metapor.Domain.user.entity.dao.ClinicTypeDoctorMappingRepository;
import com.example.metapor.Domain.user.entity.dao.DoctorRepository;
import com.example.metapor.Domain.user.entity.dao.UserRepository;
import com.example.metapor.common.auth.JwtUtils;
import com.example.metapor.common.dto.JustIdDto;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.exception.ErrorCode;
import com.example.metapor.common.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final JwtUtils jwtUtils;
    private final DoctorRepository doctorRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ClinicTypeEventMappingRepository clinicTypeEventMappingRepository;
    private final ClinicTypeDoctorMappingRepository clinicTypeDoctorMappingRepository;

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
        for (Long clinicTypeId : requestDto.clinicTypeIds()) {
            ClinicTypeDoctorMapping clinicTypeDoctorMapping = clinicTypeDoctorMappingRepository.findById(clinicTypeId).orElseThrow();
            clinicTypeEventMappingRepository.save(
                    new ClinicTypeEventMapping(clinicTypeDoctorMapping, event)
            );
        }



        return new JustIdDto(event.getId());
    }

    public GetEventPageResponseDto getEventList(String authToken) throws CustomException {
        User user = userRepository.findById(jwtUtils.getUserIdFromToken(authToken)).orElseThrow(() -> CustomException.of(ErrorCode.USER_NOT_FOUND));
        Doctor doctor = user.getDoctor();
        List<Event> acceptedEvents = eventRepository.findByDoctorAndIsAccepted(doctor);
        List<Event> pendingEvents = eventRepository.findByDoctorAndPending(doctor);
        return new GetEventPageResponseDto(
                EventInfoDto.from(acceptedEvents),
                EventInfoDto.from(pendingEvents)
        );
    }

    public EventInfoDto getEvent(String authToken, Long eventId) throws CustomException {
        jwtUtils.getUserIdFromToken(authToken);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> CustomException.of(ErrorCode.EVENT_NOT_FOUND));
        return EventInfoDto.from(event);
    }

    public SimpleResponse acceptEvent(String authToken, Long eventId) throws CustomException {
        jwtUtils.getUserIdFromToken(authToken);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> CustomException.of(ErrorCode.EVENT_NOT_FOUND));
        event.setIsAccepted(true);
        eventRepository.save(event);
        return SimpleResponse.success();
    }

    public SimpleResponse rejectEvent(String authToken, Long eventId, RejectReasonRequestDto requestDto) throws CustomException {
        jwtUtils.getUserIdFromToken(authToken);

        Event event = eventRepository.findById(eventId).orElseThrow(() -> CustomException.of(ErrorCode.EVENT_NOT_FOUND));
        event.setIsAccepted(false);
        event.setRejectReason(requestDto.reason());
        eventRepository.save(event);
        return SimpleResponse.success();
    }
}
