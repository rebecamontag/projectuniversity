package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.dto.ProfessorPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import com.rebecamontag.projectuniversity.stubs.dto.ProfessorDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.CourseStubs;
import com.rebecamontag.projectuniversity.stubs.entity.ProfessorStubs;
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
public class ProfessorServiceTests {

    @Mock
    ProfessorRepository professorRepository;

    @Mock
    CourseService courseService;

    @InjectMocks
    ProfessorService professorService;

    Professor professor;

    ProfessorDTO professorDTO;

    Course course1;

    Course course3;


    @BeforeEach
    public void setUp() {
        professor = ProfessorStubs.createProfessor();
        professorDTO = ProfessorDTOStubs.createProfessorDTO();
        course1 = CourseStubs.createCourse();
        course3 = CourseStubs.createCourse3();
    }

    @Nested
    class CreateTests {

        @Test
        public void shouldCreateProfessorWithSuccess() {
            when(professorRepository.findByDocument(professorDTO.document())).thenReturn(Optional.empty());
            when(professorRepository.save(professor)).thenReturn(professor);

            ProfessorDTO expectedProfessor = professorService.create(professorDTO);

            assertEquals(professorDTO, expectedProfessor);
        }

        @Test
        public void shouldThrowExceptionWhenDocumentAlreadyExist() {
            when(professorRepository.findByDocument(professorDTO.document())).thenReturn(Optional.of(professor));

            assertThrows(DuplicateException.class,
                    () -> professorService.create(professorDTO),
                    "Document %s already exists".formatted(professorDTO.document()));
        }
    }

    @Nested
    class FindByDocumentTests {

        @Test
        public void shouldFindByDocumentWithSuccess() {
            when(professorRepository.findByDocument(professorDTO.document())).thenReturn(Optional.of(professor));

            ProfessorDTO expectedProfessor = professorService.findByDocument(professorDTO.document());

            assertEquals(professorDTO, expectedProfessor);
            verify(professorRepository, times(1)).findByDocument(professorDTO.document());
            verifyNoMoreInteractions(professorRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidDocument() {
            when(professorRepository.findByDocument(professorDTO.document())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.findByDocument(professorDTO.document()),
                    "Professor not found with document ".concat(professorDTO.document()));
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        public void shouldFindByIdWithSuccess() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.of(professor));

            ProfessorDTO expectedProfessor = professorService.findById(professorDTO.id());

            assertEquals(professorDTO, expectedProfessor);
            verify(professorRepository, times(1)).findById(professorDTO.id());
            verifyNoMoreInteractions(professorRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidId() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.findById(professorDTO.id()),
                    "Professor not found with id ".concat(professorDTO.id().toString()));
        }
    }

    @Nested
    class FindByNameTests {

        @Test
        public void shouldFindByFirstNameWithSuccess() {
            when(professorRepository.findByFirstName(professorDTO.firstName())).thenReturn(Optional.of(professor));

            ProfessorDTO expectedProfessor = professorService.findByFirstName(professorDTO.firstName());

            assertEquals(professorDTO, expectedProfessor);
            verify(professorRepository, times(1)).findByFirstName(professorDTO.firstName());
            verifyNoMoreInteractions(professorRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidName() {
            when(professorRepository.findByFirstName(professorDTO.firstName())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.findByFirstName(professorDTO.firstName()),
                    "It was not possible to find professor called ".concat(professorDTO.firstName()));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        public void shouldFindAllWithSuccess() {
            ProfessorDTO dto = ProfessorDTOStubs.createProfessorDTO3();

            Professor professor1 = ProfessorStubs.createProfessor2();

            Page<Professor> professorPage = new PageImpl<>(List.of(professor, professor1));

            when(professorRepository.findAll(PageRequest.of(1, 2))).thenReturn(professorPage);

            ProfessorPageableResponse dtoPage = professorService.findAll(1, 2);

            assertNotNull(dtoPage);
            assertEquals(1, dtoPage.totalPages());
            assertEquals(2, dtoPage.itemsPerPage());
            assertEquals(0, dtoPage.currentPage());
            assertEquals(2, dtoPage.professorDTOList().size());
            dtoPage.professorDTOList().stream()
                    .filter(professorDTO1 -> professorDTO1.id().equals(professor.getId()))
                    .findFirst()
                    .ifPresentOrElse(professorDTO1 -> {
                        assertEquals(professor.getFirstName(), professorDTO1.firstName());
                        assertEquals(professor.getLastName(), professorDTO1.lastName());
                        assertEquals(professor.getBirthDate(), professorDTO1.birthDate());
                        assertEquals(professor.getDocument(), professorDTO1.document());
                        assertEquals(professor.getEmail(), professorDTO1.email());
                        assertEquals(professor.getGender(), professorDTO1.gender());
                    }, Assertions::fail);
            dtoPage.professorDTOList().stream()
                    .filter(professorDTO1 -> professorDTO1.id().equals(professor1.getId()))
                    .findFirst()
                    .ifPresentOrElse(professorDTO1 -> {
                        assertEquals(professor1.getFirstName(), professorDTO1.firstName());
                        assertEquals(professor1.getLastName(), professorDTO1.lastName());
                        assertEquals(professor1.getBirthDate(), professorDTO1.birthDate());
                        assertEquals(professor1.getDocument(), professorDTO1.document());
                        assertEquals(professor1.getEmail(), professorDTO1.email());
                        assertEquals(professor1.getGender(), professorDTO1.gender());
                    }, Assertions::fail);

            assertThat(dtoPage.professorDTOList()).containsExactlyInAnyOrder(professorDTO, dto);

        }
    }

    @Nested
    class UpdateTests {

        @Test
        public void shouldUpdateProfessorDataWithSuccess() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.of(professor));
            when(professorRepository.save(professor)).thenReturn(professor);

            ProfessorDTO dto = new ProfessorDTO(1,
                    "Rebeca",
                    "M. Pusinhol",
                    LocalDate.parse("2024-07-08"),
                    "12345678900",
                    "teste2@gmail.com",
                    Gender.FEMALE,
                    List.of(CourseStubs.createCourse()));

            ProfessorDTO professorDTO = professorService.update(dto.id(), dto);

            assertNotNull(professorDTO);
            assertEquals(dto.firstName(), professorDTO.firstName());
            assertEquals(dto.lastName(), professorDTO.lastName());
            assertEquals(dto.birthDate(), professorDTO.birthDate());
            assertEquals(dto.document(), professorDTO.document());
            assertEquals(dto.email(), professorDTO.email());
            assertEquals(dto.gender(), professorDTO.gender());
            assertEquals(dto.courses().get(0), professorDTO.courses().get(0));

            verify(professorRepository, times(1)).save(professor);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToUpdate() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.update(professorDTO.id(), professorDTO),
                    "Professor not found with id ".concat(professorDTO.id().toString()));
        }
    }

    @Nested
    class DeleteTests {

        @Test
        public void shouldDeleteWithSuccess() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.of(professor));
            doNothing().when(professorRepository).deleteById(professorDTO.id());

            professorService.deleteById(professorDTO.id());

            verify(professorRepository, times(1)).deleteById(professorDTO.id());
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToDelete() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.deleteById(professorDTO.id()),
                    "Professor not found with id ".concat(professorDTO.id().toString()));
        }
    }

    @Nested
    class addCourseToProfessorTests {

        @Test
        public void shouldAddCourseToProfessorWithSuccess() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.of(professor));
            when(courseService.findByIdOrElseThrow(1)).thenReturn(course1);
            when(courseService.findByIdOrElseThrow(2)).thenReturn(course3);
            List<Integer> courseIds = Arrays.asList(1, 2);

            ProfessorDTO result = professorService.addCourseToProfessor(1, courseIds);

            assertNotNull(result);
            assertEquals(2, result.courses().size());
            assertTrue(result.courses().contains(course1));
            assertTrue(result.courses().contains(course3));
            verify(professorRepository).findById(1);
            verify(courseService, times(2)).findByIdOrElseThrow(anyInt());
        }

        @Test
        public void shouldAddCourseToProfessorWhenProfessorNotFound() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.addCourseToProfessor(1, Arrays.asList(1, 2)),
                    "Professor not found with id ".concat(professorDTO.id().toString()));
        }

        @Test
        public void shouldAddCourseToProfessorWhenCourseNotFound() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.of(professor));
            when(courseService.findByIdOrElseThrow(1)).thenReturn(course1);
            when(courseService.findByIdOrElseThrow(2)).thenThrow(new NotFoundException("Course not found with id " + 2));

            List<Integer> courseIds = Arrays.asList(1, 2);

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.addCourseToProfessor(1, courseIds),
                    "Course not found with id ".concat(course3.getId().toString()));
        }
    }
}
