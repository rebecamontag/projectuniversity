package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import com.rebecamontag.projectuniversity.stubs.dto.ProfessorDTOStubs;
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
public class ProfessorServiceTests {

    @Mock
    ProfessorRepository professorRepository;

    @InjectMocks
    ProfessorService professorService;

    Professor professor;

    ProfessorDTO professorDTO;


    @BeforeEach
    public void setUp() {
        professor = ProfessorStubs.createProfessor();
        professorDTO = ProfessorDTOStubs.createProfessorDTO();
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
            when(professorRepository.findByDocument(professorDTO.document())).thenReturn(Optional.of(professorDTO));

            assertThrows(DuplicateException.class,
                    () -> professorService.create(professorDTO),
                    "Document %s already exists".formatted(professorDTO.document()));
        }
    }

    @Nested
    class FindByDocumentTests {

        @Test
        public void shouldFindByDocumentWithSuccess() {
            when(professorRepository.findByDocument(professorDTO.document())).thenReturn(Optional.of(professorDTO));

            ProfessorDTO expectedProfessor = professorService.findByDocument(professorDTO.document());

            assertEquals(professorDTO, expectedProfessor);
            verify(professorRepository, times(1)).findByDocument(professorDTO.document());
            verifyNoMoreInteractions(professorRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidDocument() {
            when(professorRepository.findByDocument(professorDTO.document())).thenThrow(new NotFoundException("Professor not found with document number " + professorDTO.document()));

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.findByDocument(professorDTO.document()),
                    "Professor não encontrado com o documento ".concat(professorDTO.document()));
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
            when(professorRepository.findById(professorDTO.id())).thenThrow(new NotFoundException("Professor not found with id " + professorDTO.id()));

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.findById(professorDTO.id()),
                    "Professor not found with id ".concat(professorDTO.id().toString()));
        }
    }

    @Nested
    class FindByNameTests {

        @Test
        public void shouldFindByNameWithSuccess() {
            when(professorRepository.findByName(professorDTO.firstName())).thenReturn(Optional.of(professorDTO));

            ProfessorDTO expectedProfessor = professorService.findByName(professorDTO.firstName());

            assertEquals(professorDTO, expectedProfessor);
            verify(professorRepository, times(1)).findByName(professorDTO.firstName());
            verifyNoMoreInteractions(professorRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidName() {
            when(professorRepository.findByName(professorDTO.firstName())).thenThrow(new NotFoundException("It was not possible to find professor called " + professorDTO.firstName()));

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.findByName(professorDTO.firstName()),
                    "It was not possible to find professor called ".concat(professorDTO.firstName()));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        public void shouldFindAllWithSuccess() {
            ProfessorDTO dto = new ProfessorDTO(1,
                    "Rebeca",
                    "M. Pusinhol",
                    LocalDate.now(),
                    "12345678900",
                    "teste@gmail.com",
                    Gender.FEMALE);

            Professor professor1 = Professor.builder()
                    .id(1)
                    .firstName("Rebeca")
                    .lastName("M. Pusinhol")
                    .birthDate(LocalDate.now())
                    .document("12345678900")
                    .email("teste@gmail.com")
                    .gender(Gender.FEMALE)
                    .build();

            Page<Professor> professorPage = new PageImpl<>(List.of(professor, professor1));

            when(professorRepository.findAll(PageRequest.of(1, 2))).thenReturn(professorPage);

            Page<ProfessorDTO> dtoPage = professorService.findAll(1, 2);

            assertNotNull(dtoPage);
            assertEquals(1, dtoPage.getTotalPages());
            assertEquals(2, dtoPage.getTotalElements());
            assertEquals(2, dtoPage.getNumberOfElements());
            assertEquals(2, dtoPage.getContent().size());
            dtoPage.getContent().stream()
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
            dtoPage.getContent().stream()
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

            assertThat(dtoPage.getContent()).containsExactlyInAnyOrder(professorDTO, dto);

        }
    }

    @Nested
    class UpdateTests {

        @Test
        public void shouldUpdateProfessorDataWithSuccess() {
            when(professorRepository.findById(professorDTO.id())).thenReturn(Optional.of(professor));

            ProfessorDTO dto = new ProfessorDTO(1,
                    "Rebeca",
                    "M. Pusinhol",
                    LocalDate.now(),
                    "12345678900",
                    "teste2@gmail.com",
                    Gender.FEMALE);

            professorService.update(dto.id(), dto);

            verify(professorRepository, times(1)).save(professor);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToUpdate() {
            when(professorRepository.findById(professorDTO.id())).thenThrow(new NotFoundException("Professor not found with id " + professorDTO.id()));

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
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToDelete() {
            when(professorRepository.findById(professorDTO.id())).thenThrow(new NotFoundException("Professor not found with id " + professorDTO.id()));
            //doNothing().when(professorRepository).deleteById(professorDTO.id());

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.deleteById(professorDTO.id()),
                    "Professor not found with id ".concat(professorDTO.id().toString()));
        }
    }
}
