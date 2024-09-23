package com.rebecamontag.projectuniversity.model.dto;

import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;

import java.time.LocalDate;
import java.util.List;

public record ProfessorDTO(
        Integer id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String document,
        String email,
        Gender gender,
        List<Course> courses
) {
}
