package com.anshulp.applicationmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Applicant {
    @Id
    // chooses the best strategy for generating primary keys based on the database dialect being used.
    // It allows the persistence provider to select an appropriate strategy for generating unique
    // identifiers, such as using auto-incrementing columns, sequences, or UUIDs, depending on the
    // capabilities of the underlying database.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String status = "Active";

    @PrePersist
    @SuppressWarnings("unused")
    public void prePersist() {
        if (status == null) {
            status = "Active";
        }
    }
}
