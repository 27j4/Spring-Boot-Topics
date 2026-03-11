package com.anshulp.springsecurity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "missions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String missionName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionType missionType;

    @Column(nullable = false)
    private String spaceAgency;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
