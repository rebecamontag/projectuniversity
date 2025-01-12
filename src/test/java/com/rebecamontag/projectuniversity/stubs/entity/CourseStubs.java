package com.rebecamontag.projectuniversity.stubs.entity;

import com.rebecamontag.projectuniversity.model.entity.Course;

import java.util.Collections;
import java.util.List;

public class CourseStubs {

    public static Course createCourse() {
        return Course.builder()
                .id(1)
                .professor(null)
                .classRoom(null)
                .name("Math")
                .description("Math lessons")
                .students(List.of())
                .build();
    }

    public static Course createCourse2() {
        return Course.builder()
                .id(1)
                .name("Math")
                .description("Math lessons")
                .build();
    }

    public static Course createCourse3() {
        return Course.builder()
                .id(2)
                .name("Chemistry")
                .description("Chemistry lessons")
                .build();
    }

    public static Course createCourse4() {
        return Course.builder()
                .id(1)
                .professor(ProfessorStubs.createProfessor())
                .classRoom(null)
                .name("Math")
                .description("Math lessons")
                .students(List.of())
                .build();
    }

    public static Course createCourse5() {
        return Course.builder()
                .id(1)
                .professor(ProfessorStubs.createProfessor())
                .classRoom(null)
                .name("Chemistry")
                .description("Chemistry lessons")
                .students(List.of())
                .build();
    }

    public static Course createCourse6() {
        return Course.builder()
                .professor(ProfessorStubs.createProfessor())
                .classRoom(null)
                .name("Math")
                .description("Math lessons")
                .students(List.of())
                .build();
    }

    public static Course createCourse7() {
        return Course.builder()
                .professor(ProfessorStubs.createProfessor())
                .classRoom(null)
                .name("Chemistry")
                .description("Chemistry lessons")
                .students(List.of())
                .build();
    }
}
