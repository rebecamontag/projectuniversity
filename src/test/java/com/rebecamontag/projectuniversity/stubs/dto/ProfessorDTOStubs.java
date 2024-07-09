package com.rebecamontag.projectuniversity.stubs.dto;

import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;

import java.time.LocalDate;

public class ProfessorDTOStubs {

    public static ProfessorDTO createProfessorDTO() {

        return new ProfessorDTO(
                1,
                "Rebeca",
                "M. Pusinhol",
                LocalDate.parse("2024-07-08"),
                "12345678900",
                "teste@gmail.com",
                Gender.FEMALE
        );
    }

    public static ProfessorDTO createProfessorDTO2() {

        return new ProfessorDTO(
                2,
                "Matheus",
                "Pusinhol",
                LocalDate.parse("2024-07-08"),
                "98765432100",
                "teste2@gmail.com",
                Gender.MALE
        );
    }

    public static ProfessorDTO createProfessorDTO3() {
        return new ProfessorDTO(1,
                "Rebeca",
                "M. Pusinhol",
                LocalDate.parse("2024-07-08"),
                "12345678900",
                "teste@gmail.com",
                Gender.FEMALE);
    }
}
