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
    private String title;

    @Column
    private String searchContent;

    public ClinicType(String title, String searchContent) {
        this.title = title;
        this.searchContent = searchContent;
    }

    public ClinicType(String title) {
        this.title = title;
    }
}
