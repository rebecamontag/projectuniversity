package com.rebecamontag.projectuniversity.stubs.entity;

import com.rebecamontag.projectuniversity.model.entity.Course;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class CourseStubs {


    public static Course createCourse1() {
        return Course.builder()
                .id(1)
                .professor(ProfessorStubs.createProfessor())
                .classRoom(any())
                .name("Math")
                .description("Math lessons")
                .students(List.of())
                .build();
    }
}
