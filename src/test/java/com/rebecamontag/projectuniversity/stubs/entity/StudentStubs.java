package com.rebecamontag.projectuniversity.stubs.entity;

import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.entity.Student;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;

import java.time.LocalDate;
import java.util.List;

public class StudentStubs {

    public static Student createStudent() {
        return Student.builder()
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

    public static Student createStudent2() {
        return Student.builder()
                .id(1)
                .firstName("Rebeca")
                .lastName("M. Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("12345678900")
                .email("teste@gmail.com")
                .gender(Gender.FEMALE)
                .build();
    }

    public static Student createStudent3() {
        return Student.builder()
                .id(2)
                .firstName("Matheus")
                .lastName("Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("98765432100")
                .email("teste2@gmail.com")
                .gender(Gender.MALE)
                .build();
    }
}
