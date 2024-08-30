package com.rebecamontag.projectuniversity.model.mapper;

import com.rebecamontag.projectuniversity.model.dto.StudentDTO;
import com.rebecamontag.projectuniversity.model.entity.Student;

public class StudentMapper {

    public static Student toEntity(StudentDTO studentDTO) {
        return Student.builder()
                .id(studentDTO.id())
                .firstName(studentDTO.firstName())
                .lastName(studentDTO.lastName())
                .document(studentDTO.document())
                .email(studentDTO.email())
                .birthDate(studentDTO.birthDate())
                .gender(studentDTO.gender())
                .build();
    }

    public static StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getBirthDate(),
                student.getDocument(),
                student.getEmail(),
                student.getGender());
    }
}
