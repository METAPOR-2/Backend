package com.example.metapor.Domain.user.entity;


import com.example.metapor.Domain.event.entity.Event;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private String imgUrl;
    private String introhospital;
    private String career;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<ClinicType> clinicTypes;

    // 추가 메서드
    public void setUser(User user) {
        this.user = user;
        user.setDoctor(this); // User 엔티티에 의사 정보를 연결
    }

    public ClinicType getMainClinicType() {
        if (clinicTypes.isEmpty()) {
            return null;
        }
        return clinicTypes.get(0);
    }
}
