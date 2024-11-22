package com.example.metapor.Domain.user.entity;


import com.example.metapor.Domain.event.entity.Event;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
@Setter// 모든 필드에 대해 getter 메소드를 자동으로 생성
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 자동으로 생성 (new Bank())
@AllArgsConstructor // 모든 필드를 매개변수로 받는 전체 생성자를 자동으로 생성
@Builder //
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String license;
    private String hospitalName;
    private String regNumber;

    private Integer patientNumberLowerLimit;
    private Integer patientNumberUpperLimit;

    private String description;

    private LocalDate startAbleDate;
    private LocalDate endAbleDate;

    private String startAbleTime;
    private String endAbleTime;

    private Float rating;
    private Integer reviewCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<ClinicTypeDoctorMapping> clinicTypeMappings;

    // 추가 메서드
    public void setUser(User user) {
        this.user = user;
        user.setDoctor(this); // User 엔티티에 의사 정보를 연결
    }

    public ClinicType getMainClinicType() {
        if (clinicTypeMappings.isEmpty()) {
            return null;
        }
        return clinicTypeMappings.get(0).getType();
    }

    public List<ClinicTypeDoctorMapping> getClinicTypeMappings() {
        if (clinicTypeMappings == null || clinicTypeMappings.isEmpty()) {
            return Collections.emptyList();
        }
        return clinicTypeMappings;
    }

    public String getAbleTime() {
        return startAbleTime + " ~ " + endAbleTime;
    }
}
