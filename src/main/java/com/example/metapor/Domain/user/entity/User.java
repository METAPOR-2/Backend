package com.example.metapor.Domain.user.entity;


import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter// 모든 필드에 대해 getter 메소드를 자동으로 생성
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 자동으로 생성 (new Bank())
@AllArgsConstructor // 모든 필드를 매개변수로 받는 전체 생성자를 자동으로 생성
@Builder //
@Entity
public class User {
    @Id
    private String id;
    private boolean isDoctor;
    private String pw;

    private String name;
    private String phone;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Doctor doctor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Patient patient;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        this.isDoctor = true; // Doctor 엔티티와 연결되면 isDoctor를 true로 설정
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        this.isDoctor = false; // Patient 엔티티와 연결되면 isDoctor를 false로 설정
    }

    // 추가 메서드
    public void setLocation(Location location) {
        this.location = location;
        location.setUser(this);
    }

    public Patient getPatient() throws CustomException {
        if (isDoctor && patient == null)
            throw CustomException.of(ErrorCode.NOT_PATIENT);
        return patient;
    }

    public Doctor getDoctor() throws CustomException {
        if (!isDoctor && doctor == null)
            throw CustomException.of(ErrorCode.NOT_DOCTOR);
        return doctor;
    }
}
