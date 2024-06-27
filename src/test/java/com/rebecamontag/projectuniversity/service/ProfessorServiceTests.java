package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.enumeration.Gender;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import com.rebecamontag.projectuniversity.stubs.dto.ProfessorDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.ProfessorStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
