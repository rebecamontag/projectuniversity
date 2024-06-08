package com.rebecamontag.projectuniversity.stubs;

import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

public class ProfessorStubs {

    public static Professor createProfessor() {
        Professor professor = Professor.builder()
                .id(001)
                .firstName("Rebeca")
                .lastName("M. Pusinhol")
                .birthDate(LocalDate.now())
                .document("12345678900")
                .email("teste@gmail.com")
                .gender(Gender.FEMALE)
                .courses(Collections.singletonList(any()))
                .build();

        return professor;
    }
}
