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
                LocalDate.now(),
                "12345678900",
                "teste@gmail.com",
                Gender.FEMALE
        );
    }
}