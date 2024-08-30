package com.rebecamontag.projectuniversity.model.mapper;

import com.rebecamontag.projectuniversity.model.dto.CourseDTO;
import com.rebecamontag.projectuniversity.model.entity.Course;

public class CourseMapper {

    public static Course toEntity(CourseDTO courseDTO) {
        return Course.builder()
                .id(courseDTO.id())
                .name(courseDTO.name())
                .description(courseDTO.description())
                .build();
    }

    public static CourseDTO toDTO(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getDescription());
    }
}
