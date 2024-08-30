package com.rebecamontag.projectuniversity.stubs.dto;

import com.rebecamontag.projectuniversity.model.dto.ClassRoomDTO;
import com.rebecamontag.projectuniversity.model.dto.StudentDTO;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;

import java.time.LocalDate;

public class ClassRoomDTOStubs {

    public static ClassRoomDTO createClassRoomDTO() {
        return new ClassRoomDTO(
                1,
                10,
                "Math Classroom"
        );
    }

    public static ClassRoomDTO createClassRoomDTO2() {
        return new ClassRoomDTO(
                2,
                20,
                "Chemistry Classroom"
        );
    }

    public static ClassRoomDTO createClassRoomDTO3() {
        return new ClassRoomDTO(
                1,
                10,
                "Math Classroom"
        );
    }
}
