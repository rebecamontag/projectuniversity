package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.CourseDTO;
import com.rebecamontag.projectuniversity.model.dto.StudentDTO;
import com.rebecamontag.projectuniversity.model.dto.StudentPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.model.entity.Student;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;
import com.rebecamontag.projectuniversity.repository.StudentRepository;
import com.rebecamontag.projectuniversity.stubs.dto.CourseDTOStubs;
import com.rebecamontag.projectuniversity.stubs.dto.StudentDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.CourseStubs;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @Mock
    StudentRepository studentRepository;

    @Mock
    CourseService courseService;

    @InjectMocks
    StudentService studentService;

    Student student;

    StudentDTO studentDTO;

    Course course1;

    Course course3;


    @BeforeEach
    public void setUp() {
        student = StudentStubs.createStudent();
        studentDTO = StudentDTOStubs.createStudentDTO();
        course1 = CourseStubs.createCourse();
        course3 = CourseStubs.createCourse3();
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
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.of(student));

            StudentDTO expectedStudent = studentService.findById(studentDTO.id());

            assertEquals(studentDTO, expectedStudent);
            verify(studentRepository, times(1)).findById(studentDTO.id());
            verifyNoMoreInteractions(studentRepository);
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
            StudentDTO dto = StudentDTOStubs.createStudentDTO6();

            Student student1 = StudentStubs.createStudent6();

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
                    Gender.FEMALE,
                    List.of(CourseDTOStubs.createCourseDTO()));

            StudentDTO studentDTO = studentService.update(dto.id(), dto);

            assertNotNull(studentDTO);
            assertEquals(dto.firstName(), studentDTO.firstName());
            assertEquals(dto.lastName(), studentDTO.lastName());
            assertEquals(dto.birthDate(), studentDTO.birthDate());
            assertEquals(dto.document(), studentDTO.document());
            assertEquals(dto.email(), studentDTO.email());
            assertEquals(dto.gender(), studentDTO.gender());
            assertEquals(dto.courses().get(0), studentDTO.courses().get(0));

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

    @Nested
    class AddCourseToStudentTests {

        @Test
        public void shouldAddCourseToProfessorWithSuccess() {
            StudentDTO dto = StudentDTOStubs.createStudentDTO4();
            Student stu = StudentStubs.createStudent();

            when(studentRepository.findById(dto.id())).thenReturn(Optional.of(stu));
            when(courseService.findByIdOrElseThrow(course1.getId())).thenReturn(course1);
            when(courseService.findByIdOrElseThrow(course3.getId())).thenReturn(course3);
            List<Integer> courseIds = Arrays.asList(course1.getId(), course3.getId());

            StudentDTO result = studentService.addCourseToStudent(1, courseIds);

            CourseDTO c1 = CourseDTOStubs.createCourseDTO3();
            CourseDTO c2 = CourseDTOStubs.createCourseDTO2();

            assertNotNull(result);
            assertEquals(2, result.courses().size());
            assertTrue(result.courses().contains(c1));
            assertTrue(result.courses().contains(c2));
            verify(studentRepository).findById(1);
            verify(courseService, times(2)).findByIdOrElseThrow(anyInt());
        }

        @Test
        public void shouldAddCourseToProfessorWhenProfessorNotFound() {
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> studentService.addCourseToStudent(1, Arrays.asList(1, 2)),
                    "Student not found with id ".concat(studentDTO.id().toString()));
        }

        @Test
        public void shouldAddCourseToProfessorWhenCourseNotFound() {
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.of(student));
            when(courseService.findByIdOrElseThrow(1)).thenReturn(course1);
            when(courseService.findByIdOrElseThrow(2)).thenThrow(new NotFoundException("Course not found with id " + 2));

            List<Integer> courseIds = Arrays.asList(1, 2);

            assertThrows(
                    NotFoundException.class,
                    () -> studentService.addCourseToStudent(1, courseIds),
                    "Course not found with id ".concat(course3.getId().toString()));
        }
    }

    @Nested
    class DeleteCourseFromProfessor {

        @Test
        public void shouldDeleteCourseFromProfessorWithSuccess() {
            StudentDTO dto = StudentDTOStubs.createStudentDTO5();
            Student stu = StudentStubs.createStudent5();

            when(studentRepository.findById(dto.id())).thenReturn(Optional.of(stu));
            when(courseService.findByIdOrElseThrow(course1.getId())).thenReturn(course1);

            studentService.deleteCourseFromStudent(1, course1.getId());

            List<Course> coursesAfterDeletion = stu.getCourses();
            assertEquals(1, coursesAfterDeletion.size());
            assertFalse(coursesAfterDeletion.stream().anyMatch(c -> c.getId().equals(course1.getId())));
        }

        @Test
        public void shouldDeleteCourseFromProfessorWhenProfessorNotFound() {
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> studentService.deleteCourseFromStudent(1, course1.getId()),
                    "Student not found with id ".concat(studentDTO.id().toString()));
        }

        @Test
        public void shouldDeleteCourseFromProfessorWhenCourseNotFound() {
            when(studentRepository.findById(studentDTO.id())).thenReturn(Optional.of(student));
            when(courseService.findByIdOrElseThrow(course3.getId())).thenThrow(new NotFoundException("Course not found with id " + course3.getId()));

            assertThrows(
                    NotFoundException.class,
                    () -> studentService.deleteCourseFromStudent(1, course3.getId()),
                    "Course not found with id ".concat(course3.getId().toString()));
        }
    }
}
