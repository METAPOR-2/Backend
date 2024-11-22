package com.example.metapor.Domain;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter// 모든 필드에 대해 getter 메소드를 자동으로 생성
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 자동으로 생성 (new Bank())
@AllArgsConstructor // 모든 필드를 매개변수로 받는 전체 생성자를 자동으로 생성
@Builder //
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String type;
    private String license;
    private String hospitalName;
    private String regNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // 추가 메서드
    public void setUser(User user) {
        this.user = user;
        user.setDoctor(this); // User 엔티티에 의사 정보를 연결
    }
}