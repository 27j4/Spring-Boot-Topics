package com.anshulp.applicationmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicantResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String status;
}
