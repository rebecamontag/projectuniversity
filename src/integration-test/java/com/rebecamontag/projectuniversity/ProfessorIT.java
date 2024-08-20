package com.rebecamontag.projectuniversity;

import com.rebecamontag.projectuniversity.stubs.entity.ProfessorStubs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfessorIT extends BaseIT {

    @Nested
    class CreateTest {

        @AfterEach
        void cleanUpDatabase() {
            professorRepository.deleteAll();
        }

        @Test
        void shouldCreate() throws Exception {
            String request = """
                    {
                            "id":1,
                            "firstName":"Matheus",
                            "lastName":"Pusinhol",
                            "birthDate":"2024-07-08",
                            "document":"98765432100",
                            "email":"teste2@gmail.com",
                            "gender":"MALE"
                                
                    }
                    """;

            mockMvc.perform(MockMvcRequestBuilders.post("/professors")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.header().exists("Location"))
                    .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/professors/1"));
        }
    }

    @Nested
    class FindByDocumentTest {

        @BeforeEach
        void setUpDatabase() {
            professorRepository.save(ProfessorStubs.createProfessor3());
        }

        @AfterEach
        void cleanUpDatabase() {
            professorRepository.deleteAll();
        }

        @Test
        void shouldFindByDocument() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/professors/document/98765432100"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":1,
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

    @Nested
    class FindByIdTest {

        @BeforeEach
        void setUpDatabase() {
            professorRepository.save(ProfessorStubs.createProfessor());
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
                            "id":1,
                            "firstName":"Rebeca",
                            "lastName":"M. Pusinhol",
                            "birthDate":"2024-07-08",
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
    class FindByFirstNameTest {

        @BeforeEach
        void setUpDatabase() {
            professorRepository.save(ProfessorStubs.createProfessor3());
        }

        @AfterEach
        void cleanUpDatabase() {
            professorRepository.deleteAll();
        }

        @Test
        void shouldFindByFirstName() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/professors/name/Matheus"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":1,
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

//    @Nested
//    class FindAllTest {
//
//        @BeforeEach
//        void setUpDatabase() {
//            professorRepository.saveAll(ProfessorStubs.createProfessor4());
//        }
//
//        @AfterEach
//        void cleanUpDatabase() {
//            professorRepository.deleteAll();
//        }
//
//        @Test
//        void shouldFindAll() throws Exception {
//            String result = mockMvc.perform(MockMvcRequestBuilders.get("/professors?page=0&size=10"))
//                    .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andReturn()
//                    .getResponse()
//                    .getContentAsString();
//
//            assertNotNull(result);
//            JSONAssert.assertEquals("""
//                    {
//                  "totalPages":1,
//                  "itemsPerPage":2,
//                  "currentPage":0,
//                  "professorDTOList":
//                  [
//                        {
//                            "id":1,
//                            "firstName":"Matheus",
//                            "lastName":"Pusinhol",
//                            "birthDate":"2024-07-08",
//                            "document":"98765432100",
//                            "email":"teste2@gmail.com",
//                            "gender":"MALE"
//                            },
//                        {
//                            "id":2,
//                            "firstName":"Rebeca",
//                            "lastName":"M. Pusinhol",
//                            "birthDate":"2024-07-08",
//                            "document":"12345678900",
//                            "email":"teste@gmail.com",
//                            "gender":"FEMALE"
//                    }
//                    ]}
//                    """,
//                    result,
//                    JSONCompareMode.STRICT);
//        }
//    }

    @Nested
    class UpdateTest {

        @BeforeEach
        void setUpDatabase() {
            professorRepository.save(ProfessorStubs.createProfessor());
        }

        @AfterEach
        void cleanUpDatabase() {
            professorRepository.deleteAll();
        }

        @Test
        void shouldUpdate() throws Exception {
            String request = """
                    {
                            "id":1,
                            "firstName":"Rebeca",
                            "lastName":"Pusinhol",
                            "birthDate":"2024-07-08",
                            "document":"12345678900",
                            "email":"teste@gmail.com",
                            "gender":"FEMALE"
                                
                    }
                    """;

            String result = mockMvc.perform(MockMvcRequestBuilders.put("/professors/1")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":1,
                            "firstName":"Rebeca",
                            "lastName":"Pusinhol",
                            "birthDate":"2024-07-08",
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
    class DeleteTest {

        @BeforeEach
        void setUpDatabase() {
            professorRepository.save(ProfessorStubs.createProfessor());
        }

        @Test
        void shouldDelete() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/professors/1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

        }
    }
}
