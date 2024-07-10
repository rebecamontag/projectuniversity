package com.rebecamontag.projectuniversity.model.dto;

public record CourseDTO(
        Integer id,
        ProfessorDTO professorDTO,
        String name,
        String description
) {
}
