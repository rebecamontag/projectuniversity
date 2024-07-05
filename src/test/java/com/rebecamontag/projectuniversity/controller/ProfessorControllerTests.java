package com.rebecamontag.projectuniversity.controller;

import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.dto.ProfessorPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.service.ProfessorService;
import com.rebecamontag.projectuniversity.stubs.dto.ProfessorDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.ProfessorStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(value = ProfessorController.class)
public class ProfessorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfessorService professorService;

    Professor professor;

    ProfessorDTO professorDTO;


    @BeforeEach
    public void setUp() {
        professor = ProfessorStubs.createProfessor();
        professorDTO = ProfessorDTOStubs.createProfessorDTO();
    }


//    @Nested
//    class FindByDocumentTests {
//
//        @Test
//        public void shouldFindByDocumentWithSuccess() {
//            when(professorService.findByDocument(professorDTO.document())).thenReturn(professorDTO);
//            ResponseEntity<ProfessorDTO> expectedProfessorDTO = professorController.findByDocument(professorDTO.document());
//            Assertions.assertThat(expectedProfessorDTO).isNotNull();
//            Assertions.assertThat(expectedProfessorDTO.equals(professorDTO));
//        }
//    }

    @Nested
    class FindByDocumentTest {

        @Test
        void shouldFindByDocument() throws Exception {
            when(professorService.findByDocument(professorDTO.document())).thenReturn(professorDTO);

            String result = mockMvc.perform(MockMvcRequestBuilders.get("/professors/12345678900"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id": 1,
                            "firstName":"Rebeca",
                            "lastName":"M. Pusinhol",
                            "birthDate":"2024-07-03",
                            "document":"12345678900",
                            "email":"teste@gmail.com",
                            "gender":"FEMALE"
                                
                    }
                    """,
                    result,
                    JSONCompareMode.STRICT);
        }
    }

    @Nested
    class FindAllTest {

        @Test
        void shouldFindAll() throws Exception {
            ProfessorPageableResponse professorPage = new ProfessorPageableResponse(
                    1,
                    2,
                    0,
                    List.of(professorDTO, ProfessorDTOStubs.createProfessorDTO2()));
            when(professorService.findAll(0, 10)).thenReturn(professorPage);

            String result = mockMvc.perform(MockMvcRequestBuilders.get("/professors?page=0&size=10"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {"totalPages":1,"itemsPerPage":2,"currentPage":0,"professorDTOList":[{"id":1,"firstName":"Rebeca","lastName":"M. Pusinhol","birthDate":"2024-07-03","document":"12345678900","email":"teste@gmail.com","gender":"FEMALE"},{"id":2,"firstName":"Matheus","lastName":"Pusinhol","birthDate":"2024-07-03","document":"98765432100","email":"teste2@gmail.com","gender":"MALE"}]}""", result, JSONCompareMode.STRICT);
        }
    }

    @Nested
    class DeleteTests {

        @Test
        public void shouldDeleteWithSuccess() throws Exception {
            doNothing().when(professorService).deleteById(professorDTO.id());

            mockMvc.perform(MockMvcRequestBuilders.delete("/professors/1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

            verify(professorService, times(1)).deleteById(professorDTO.id());
        }
    }
}
