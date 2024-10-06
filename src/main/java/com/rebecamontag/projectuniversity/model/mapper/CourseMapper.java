package com.rebecamontag.projectuniversity.model.mapper;

import com.rebecamontag.projectuniversity.model.dto.CourseDTO;
import com.rebecamontag.projectuniversity.model.entity.Course;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Course> fromCourseDTOToEntity(List<CourseDTO> courseDTOList) {
       return courseDTOList.stream().map(CourseMapper::toEntity).collect(Collectors.toList());
    }

    public static List<CourseDTO> fromCourseToDTO(List<Course> courseList) {
        return courseList.stream().map(CourseMapper::toDTO).collect(Collectors.toList());
    }
}