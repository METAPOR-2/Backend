package com.example.metapor.Domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClinicTypeDoctorMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clinic_type_id")
    private ClinicType clinicType;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
