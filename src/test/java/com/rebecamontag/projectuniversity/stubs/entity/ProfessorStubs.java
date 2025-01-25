package com.rebecamontag.projectuniversity.stubs.entity;

import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;

import java.time.LocalDate;
import java.util.List;

public class ProfessorStubs {

    public static Professor createProfessor() {
        return Professor.builder()
                .id(1)
                .firstName("Rebeca")
                .lastName("M. Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("12345678900")
                .email("teste@gmail.com")
                .gender(Gender.FEMALE)
                .courses(List.of(CourseStubs.createCourse()))
                .build();
    }

    public static Professor createProfessor2() {
        return Professor.builder()
                .id(1)
                .firstName("Rebeca")
                .lastName("M. Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("12345678900")
                .email("teste@gmail.com")
                .gender(Gender.FEMALE)
                .courses(List.of(CourseStubs.createCourse3()))
                .build();
    }

    public static Professor createProfessor3() {
        Professor professor = Professor.builder()
                .firstName("Rebeca")
                .lastName("M. Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("12345678900")
                .email("teste@gmail.com")
                .gender(Gender.FEMALE)
                .build();
        professor.setCourses(List.of(CourseStubs.createCourse7()));

        return professor;
    }

    public static Professor createProfessor4() {
        return Professor.builder()
                .id(1)
                .firstName("Rebeca")
                .lastName("M. Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("12345678900")
                .email("teste@gmail.com")
                .gender(Gender.FEMALE)
                .courses(List.of())
                .build();
    }

    public static Professor createProfessor5() {
        return Professor.builder()
                .id(1)
                .firstName("Rebeca")
                .lastName("M. Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("12345678900")
                .email("teste@gmail.com")
                .gender(Gender.FEMALE)
                .courses(List.of(CourseStubs.createCourse(), CourseStubs.createCourse3()))
                .build();
    }

    public static Professor createProfessor6() {
        return Professor.builder()
                .id(2)
                .firstName("Matheus")
                .lastName("Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("98765432100")
                .email("teste2@gmail.com")
                .gender(Gender.MALE)
                .courses(List.of(CourseStubs.createCourse3()))
                .build();
    }

    public static Professor createProfessor7() {
        return Professor.builder()
                .id(1)
                .firstName("Matheus")
                .lastName("Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("98765432100")
                .email("teste2@gmail.com")
                .gender(Gender.MALE)
                .courses(List.of())
                .build();
    }

    public static Professor createProfessor8() {
        Professor professor = Professor.builder()
                .id(1)
                .firstName("Rebeca")
                .lastName("M. Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("12345678900")
                .email("teste@gmail.com")
                .gender(Gender.FEMALE)
                .build();
        professor.setCourses(List.of(CourseStubs.createCourse4()));

        return professor;
    }

    public static Professor createProfessor9() {
        Professor professor = Professor.builder()
                .id(2)
                .firstName("Matheus")
                .lastName("Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("98765432100")
                .email("teste2@gmail.com")
                .gender(Gender.MALE)
                .build();
        professor.setCourses(List.of(CourseStubs.createCourse4()));

        return professor;
    }

    public static Professor createProfessor10() {
        Professor professor = Professor.builder()
                .firstName("Matheus")
                .lastName("Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("98765432100")
                .email("teste2@gmail.com")
                .gender(Gender.MALE)
                .build();
        professor.setCourses(List.of(CourseStubs.createCourse6()));

        return professor;
    }
}
