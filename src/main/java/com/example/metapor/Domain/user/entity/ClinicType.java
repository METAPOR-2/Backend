package com.example.metapor.Domain.user.entity;

import com.example.metapor.Domain.user.dto.ClinicTypeRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClinicType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Column
    private int price;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public ClinicType(String type, int price, String description, Doctor doctor) {
        this.type = type;
        this.price = price;
        this.description = description;
        this.doctor = doctor;
    }

    public static ClinicType fromDto(ClinicTypeRequestDto dto, Doctor doctor) {
        return new ClinicType(dto.title(), dto.price(), dto.description(), doctor);

    }
}
