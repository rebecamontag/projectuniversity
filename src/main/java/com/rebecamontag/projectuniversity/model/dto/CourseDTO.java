package com.rebecamontag.projectuniversity.model.dto;

import com.rebecamontag.projectuniversity.model.entity.ClassRoom;
import com.rebecamontag.projectuniversity.model.entity.Professor;

public record CourseDTO(
        Integer id,
        Professor professor,
        ClassRoom classRoom,
        String name,
        String description
) {
}
