package com.rebecamontag.projectuniversity.stubs.entity;

import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
                .courses(List.of())
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
                .build();
    }

    public static Professor createProfessor3() {
        return Professor.builder()
                .id(2)
                .firstName("Matheus")
                .lastName("Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("98765432100")
                .email("teste2@gmail.com")
                .gender(Gender.MALE)
                .build();
    }

    public static List<Professor> createProfessor4() {
        Professor professor1 = Professor.builder()
                .id(2)
                .firstName("Matheus")
                .lastName("Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("98765432100")
                .email("teste2@gmail.com")
                .gender(Gender.MALE)
                .build();

        Professor professor2 = Professor.builder()
                .id(1)
                .firstName("Rebeca")
                .lastName("M. Pusinhol")
                .birthDate(LocalDate.parse("2024-07-08"))
                .document("12345678900")
                .email("teste@gmail.com")
                .gender(Gender.FEMALE)
                .build();

        List<Professor> professors = new ArrayList<>();
        professors.add(professor1);
        professors.add(professor2);
        return professors;
    }
}
