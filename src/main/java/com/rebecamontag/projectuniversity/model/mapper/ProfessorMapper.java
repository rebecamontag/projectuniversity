package com.rebecamontag.projectuniversity.model.mapper;

import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.model.entity.Professor;

import java.util.List;

public class ProfessorMapper {

    public static Professor toEntity(ProfessorDTO professorDTO) {
        return Professor.builder()
                .id(professorDTO.id())
                .firstName(professorDTO.firstName())
                .lastName(professorDTO.lastName())
                .document(professorDTO.document())
                .email(professorDTO.email())
                .birthDate(professorDTO.birthDate())
                .gender(professorDTO.gender())
                .courses(CourseMapper.fromCourseDTOToEntity(professorDTO.courses()))
                .build();
    }

    public static ProfessorDTO toDTO(Professor professor) {
        return new ProfessorDTO(
                professor.getId(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getBirthDate(),
                professor.getDocument(),
                professor.getEmail(),
                professor.getGender(),
                CourseMapper.fromCourseToDTO(professor.getCourses()));
    }
}
