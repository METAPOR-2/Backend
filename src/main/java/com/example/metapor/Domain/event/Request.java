package com.example.metapor.Domain.event;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter// 모든 필드에 대해 getter 메소드를 자동으로 생성
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 자동으로 생성 (new Bank())
@AllArgsConstructor // 모든 필드를 매개변수로 받는 전체 생성자를 자동으로 생성
@Builder //
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String applicantName; //신청자 이름

    @Column(name = "number_of_applicants", nullable = false)
    private Integer numberOfApplicants; //신청 인원

    @Column(name = "application_date_time", nullable = false)
    private LocalDateTime applicationDateTime; // 신청 날짜 및 시간

    @Column(name = "region", nullable = false)
    private String region; // 지역

    @Column(name = "application_item", nullable = false)
    private String applicationItem; // 신청 항목
}
