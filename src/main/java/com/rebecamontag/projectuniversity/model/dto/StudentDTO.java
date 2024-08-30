package com.rebecamontag.projectuniversity.model.dto;

import com.rebecamontag.projectuniversity.model.enumeration.Gender;

import java.time.LocalDate;

public record StudentDTO(
        Integer id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String document,
        String email,
        Gender gender
) {
}
