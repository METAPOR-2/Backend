package com.example.metapor.Domain.user.entity;

import com.example.metapor.Domain.user.dto.ClinicTypeRequestDto;
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
    private ClinicType type;

    @Column
    private int lowPrice;

    @Column
    private int highPrice;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public ClinicTypeDoctorMapping(ClinicType type, int lowPrice, int highPrice, String description, Doctor doctor) {
        this.type = type;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.description = description;
        this.doctor = doctor;
    }

}
