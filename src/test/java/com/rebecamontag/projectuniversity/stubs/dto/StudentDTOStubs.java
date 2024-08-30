package com.rebecamontag.projectuniversity.stubs.dto;

import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.dto.StudentDTO;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;

import java.time.LocalDate;

public class StudentDTOStubs {

    public static StudentDTO createStudentDTO() {
        return new StudentDTO(
                1,
                "Rebeca",
                "M. Pusinhol",
                LocalDate.parse("2024-07-08"),
                "12345678900",
                "teste@gmail.com",
                Gender.FEMALE
        );
    }

    public static StudentDTO createStudentDTO2() {
        return new StudentDTO(
                2,
                "Matheus",
                "Pusinhol",
                LocalDate.parse("2024-07-08"),
                "98765432100",
                "teste2@gmail.com",
                Gender.MALE
        );
    }

    public static StudentDTO createStudentDTO3() {
        return new StudentDTO(1,
                "Rebeca",
                "M. Pusinhol",
                LocalDate.parse("2024-07-08"),
                "12345678900",
                "teste@gmail.com",
                Gender.FEMALE);
    }
}
