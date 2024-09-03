package com.rebecamontag.projectuniversity.stubs.dto;

import com.rebecamontag.projectuniversity.model.dto.CourseDTO;

public class CourseDTOStubs {

    public static CourseDTO createCourseDTO() {

        return new CourseDTO(
                1,
                "Math",
                "Math lessons"
        );
    }

    public static CourseDTO createCourseDTO2() {

        return new CourseDTO(
                2,
                "Chemistry",
                "Chemistry lessons"
        );
    }

    public static CourseDTO createCourseDTO3() {
        return new CourseDTO(1,
                "Math",
                "Math lessons"
        );
    }
}
