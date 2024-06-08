package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import com.rebecamontag.projectuniversity.stubs.ProfessorStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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


    @BeforeEach
    public void setUp() {
        professor = ProfessorStubs.createProfessor();
    }

    @Nested
    class FindByDocumentTest {

        @Test
        public void shouldFindByDocumentWithSuccess() {
            when(professorRepository.findByDocument(professor.getDocument())).thenReturn(Optional.of(professor));

            Professor expectedProfessor = professorService.findByDocument(professor.getDocument());

            assertEquals(professor, expectedProfessor);
            verify(professorRepository, times(1)).findByDocument(professor.getDocument());
            verifyNoMoreInteractions(professorRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidDocument() {
            when(professorRepository.findByDocument(professor.getDocument())).thenThrow(new NotFoundException("Professor não encontrado com o documento " + professor.getDocument()));

            assertThrows(
                    NotFoundException.class,
                    () -> professorService.findByDocument(professor.getDocument()),
                    "Professor não encontrado com o documento ".concat(professor.getDocument()));
        }
    }
}
