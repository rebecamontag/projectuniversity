package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.dto.ProfessorPageableResponse;
import com.rebecamontag.projectuniversity.model.dto.StudentDTO;
import com.rebecamontag.projectuniversity.model.dto.StudentPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.entity.Student;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import com.rebecamontag.projectuniversity.repository.StudentRepository;
import com.rebecamontag.projectuniversity.stubs.dto.ProfessorDTOStubs;
import com.rebecamontag.projectuniversity.stubs.dto.StudentDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.ProfessorStubs;
import com.rebecamontag.projectuniversity.stubs.entity.StudentStubs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    Student student;

    StudentDTO studentDTO;


    @BeforeEach
    public void setUp() {
        student = StudentStubs.createStudent();
        studentDTO = StudentDTOStubs.createStudentDTO();
    }

    @Nested
    class CreateTests {

        @Test
        public void shouldCreateStudentWithSuccess() {
            when(studentRepository.findByDocument(studentDTO.document())).thenReturn(Optional.empty());
            when(studentRepository.save(student)).thenReturn(student);

            StudentDTO expectedStudent = studentService.create(studentDTO);

            assertEquals(studentDTO, expectedStudent);
        }

        @Test
        public void shouldThrowExceptionWhenDocumentAlreadyExist() {
            when(studentRepository.findByDocument(studentDTO.document())).thenReturn(Optional.of(student));

            assertThrows(DuplicateException.class,
                    () -> studentService.create(studentDTO),
                    "Document %s already exists".formatted(studentDTO.document()));
        }
    }

    @Nested
    class FindByDocumentTests {

        @Test
        public void shouldFindByDocumentWithSuccess() {
            when(studentRepository.findByDocument(studentDTO.document())).thenReturn(Optional.of(student));

            StudentDTO expectedStudent = studentService.findByDocument(studentDTO.document());

            assertEquals(studentDTO, expectedStudent);
            verify(studentRepository, times(1)).findByDocument(studentDTO.document());
            verifyNoMoreInteractions(studentRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidDocument() {
            when(studentRepository.findByDocument(studentDTO.document())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> studentService.findByDocument(studentDTO.document()),
                    "Student not found with document ".concat(studentDTO.document()));
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        public void shouldFindByIdWithSuccess() {
        }

        @Test
        public void shouldThrowExceptionWhenNotValidId() {
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> studentService.findById(studentDTO.id()),
                    "Student not found with id ".concat(studentDTO.id().toString()));
        }
    }

    @Nested
    class FindByNameTests {

        @Test
        public void shouldFindByFirstNameWithSuccess() {
            when(studentRepository.findByFirstName(studentDTO.firstName())).thenReturn(Optional.of(student));

            StudentDTO expectedStudent = studentService.findByFirstName(studentDTO.firstName());

            assertEquals(studentDTO, expectedStudent);
            verify(studentRepository, times(1)).findByFirstName(studentDTO.firstName());
            verifyNoMoreInteractions(studentRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidName() {
            when(studentRepository.findByFirstName(studentDTO.firstName())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> studentService.findByFirstName(studentDTO.firstName()),
                    "It was not possible to find student called ".concat(studentDTO.firstName()));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        public void shouldFindAllWithSuccess() {
            StudentDTO dto = StudentDTOStubs.createStudentDTO3();

            Student student1 = StudentStubs.createStudent2();

            Page<Student> studentPage = new PageImpl<>(List.of(student, student1));

            when(studentRepository.findAll(PageRequest.of(1, 2))).thenReturn(studentPage);

            StudentPageableResponse dtoPage = studentService.findAll(1, 2);

            assertNotNull(dtoPage);
            assertEquals(1, dtoPage.totalPages());
            assertEquals(2, dtoPage.itemsPerPage());
            assertEquals(0, dtoPage.currentPage());
            assertEquals(2, dtoPage.studentDTOList().size());
            dtoPage.studentDTOList().stream()
                    .filter(studentDTO1 -> studentDTO1.id().equals(student.getId()))
                    .findFirst()
                    .ifPresentOrElse(studentDTO1 -> {
                        assertEquals(student.getFirstName(), studentDTO1.firstName());
                        assertEquals(student.getLastName(), studentDTO1.lastName());
                        assertEquals(student.getBirthDate(), studentDTO1.birthDate());
                        assertEquals(student.getDocument(), studentDTO1.document());
                        assertEquals(student.getEmail(), studentDTO1.email());
                        assertEquals(student.getGender(), studentDTO1.gender());
                    }, Assertions::fail);
            dtoPage.studentDTOList().stream()
                    .filter(studentDTO1 -> studentDTO1.id().equals(student1.getId()))
                    .findFirst()
                    .ifPresentOrElse(studentDTO1 -> {
                        assertEquals(student1.getFirstName(), studentDTO1.firstName());
                        assertEquals(student1.getLastName(), studentDTO1.lastName());
                        assertEquals(student1.getBirthDate(), studentDTO1.birthDate());
                        assertEquals(student1.getDocument(), studentDTO1.document());
                        assertEquals(student1.getEmail(), studentDTO1.email());
                        assertEquals(student1.getGender(), studentDTO1.gender());
                    }, Assertions::fail);

            assertThat(dtoPage.studentDTOList()).containsExactlyInAnyOrder(studentDTO, dto);

        }
    }

    @Nested
    class UpdateTests {

        @Test
        public void shouldUpdateProfessorDataWithSuccess() {
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.of(student));
            when(studentRepository.save(student)).thenReturn(student);

            StudentDTO dto = new StudentDTO(1,
                    "Rebeca",
                    "M. Pusinhol",
                    LocalDate.now(),
                    "12345678900",
                    "teste2@gmail.com",
                    Gender.FEMALE);

            StudentDTO studentDTO = studentService.update(dto.id(), dto);

            assertNotNull(studentDTO);
            assertEquals(dto.firstName(), studentDTO.firstName());
            assertEquals(dto.lastName(), studentDTO.lastName());
            assertEquals(dto.birthDate(), studentDTO.birthDate());
            assertEquals(dto.document(), studentDTO.document());
            assertEquals(dto.email(), studentDTO.email());
            assertEquals(dto.gender(), studentDTO.gender());

            verify(studentRepository, times(1)).save(student);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToUpdate() {
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> studentService.update(studentDTO.id(), studentDTO),
                    "Student not found with id ".concat(studentDTO.id().toString()));
        }
    }

    @Nested
    class DeleteTests {

        @Test
        public void shouldDeleteWithSuccess() {
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.of(student));
            doNothing().when(studentRepository).deleteById(studentDTO.id());

            studentService.deleteById(studentDTO.id());

            verify(studentRepository, times(1)).deleteById(studentDTO.id());
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToDelete() {
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> studentService.deleteById(studentDTO.id()),
                    "Student not found with id ".concat(studentDTO.id().toString()));
        }
    }
}
