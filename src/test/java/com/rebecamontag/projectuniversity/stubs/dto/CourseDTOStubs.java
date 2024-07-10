package com.rebecamontag.projectuniversity.stubs.dto;

import com.rebecamontag.projectuniversity.model.dto.CourseDTO;

public class CourseDTOStubs {


    public static CourseDTO createCourseDTO1() {
        return new CourseDTO(
                1,
                ProfessorDTOStubs.createProfessorDTO(),
                "Math",
                "Math lessons"
        );
    }
}
