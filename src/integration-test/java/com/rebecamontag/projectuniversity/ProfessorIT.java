package com.rebecamontag.projectuniversity;

import com.rebecamontag.projectuniversity.stubs.entity.ProfessorStubs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfessorIT extends BaseIT {

    @Nested
    class FindByIdTest {

        @BeforeEach
        void setUpDatabase() {
            professorRepository.save(ProfessorStubs.createProfessor3());
        }

        @AfterEach
        void cleanUpDatabase() {
            professorRepository.deleteAll();
        }

        @Test
        void shouldFindById() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/professors/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":2,
                            "firstName":"Matheus",
                            "lastName":"Pusinhol",
                            "birthDate":"2024-07-08",
                            "document":"98765432100",
                            "email":"teste2@gmail.com",
                            "gender":"MALE"
                                
                    }
                    """,
                    result,
                    JSONCompareMode.STRICT);
        }
    }
}
