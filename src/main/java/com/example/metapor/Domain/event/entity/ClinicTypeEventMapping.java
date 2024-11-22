package com.example.metapor.Domain.event.entity;

import com.example.metapor.Domain.user.entity.ClinicType;
import com.example.metapor.Domain.user.entity.ClinicTypeDoctorMapping;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClinicTypeEventMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clinic_type_id")
    private ClinicTypeDoctorMapping clinicTypeDoctorMapping;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
