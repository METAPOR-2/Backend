package com.example.metapor.Domain.event.entity;

import com.example.metapor.Domain.user.entity.Doctor;
import com.example.metapor.Domain.user.entity.Patient;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter// 모든 필드에 대해 getter 메소드를 자동으로 생성
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 자동으로 생성 (new Bank())
@AllArgsConstructor // 모든 필드를 매개변수로 받는 전체 생성자를 자동으로 생성
@Builder //
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer numberOfApplicants; //신청 인원

    @Column
    private LocalDateTime applicationDateTime; // 신청 날짜 및 시간

    @Column
    private String region; // 지역

    @Column
    private String address; // 주소

    @Column
    private String comment; // 코멘트

    @OneToMany(mappedBy = "event")
    private List<ClinicTypeEventMapping> clinicTypeEventMappings = new ArrayList<>();

    //Patients 연결
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    //Doctor 연결
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}