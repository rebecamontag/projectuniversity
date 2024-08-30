package com.rebecamontag.projectuniversity;

import com.rebecamontag.projectuniversity.model.entity.Student;
import com.rebecamontag.projectuniversity.stubs.entity.StudentStubs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentIT extends BaseIT {

    @Nested
    class CreateTest {

        @AfterEach
        void cleanUpDatabase() {
            studentRepository.deleteAll();
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

            mockMvc.perform(MockMvcRequestBuilders.post("/students")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.header().exists("Location"))
                    .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/students/1"));
        }
    }

    @Nested
    class FindByDocumentTest {

        @BeforeEach
        void setUpDatabase() {
            studentRepository.save(StudentStubs.createStudent3());
        }

        @AfterEach
        void cleanUpDatabase() {
            studentRepository.deleteAll();
        }

        @Test
        void shouldFindByDocument() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/students/document/98765432100"))
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
            studentRepository.save(StudentStubs.createStudent());
        }

        @AfterEach
        void cleanUpDatabase() {
            studentRepository.deleteAll();
        }

        @Test
        void shouldFindById() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
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
            studentRepository.save(StudentStubs.createStudent3());
        }

        @AfterEach
        void cleanUpDatabase() {
            studentRepository.deleteAll();
        }

        @Test
        void shouldFindByFirstName() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/students/name/Matheus"))
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
    class FindAllTest {

        @BeforeEach
        void setUpDatabase() {
            Student student1 = StudentStubs.createStudent3();
            student1.setId(null);
            Student student2 = StudentStubs.createStudent2();
            student2.setId(null);
            studentRepository.saveAll(List.of(student1, student2));
        }

        @AfterEach
        void cleanUpDatabase() {
            studentRepository.deleteAll();
        }

        @Test
        void shouldFindAll() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/students?page=0&size=10"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                  {
                  "totalPages":1,
                  "itemsPerPage":2,
                  "currentPage":0,
                  "studentDTOList":
                        [
                            {
                            "id":1,
                            "firstName":"Matheus",
                            "lastName":"Pusinhol",
                            "birthDate":"2024-07-08",
                            "document":"98765432100",
                            "email":"teste2@gmail.com",
                            "gender":"MALE"
                            },
                        {
                            "id":2,
                            "firstName":"Rebeca",
                            "lastName":"M. Pusinhol",
                            "birthDate":"2024-07-08",
                            "document":"12345678900",
                            "email":"teste@gmail.com",
                            "gender":"FEMALE"
                    }
                    ]}
                    """,
                    result,
                    JSONCompareMode.STRICT);
        }
    }

    @Nested
    class UpdateTest {

        @BeforeEach
        void setUpDatabase() {
            studentRepository.save(StudentStubs.createStudent());
        }

        @AfterEach
        void cleanUpDatabase() {
            studentRepository.deleteAll();
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

            String result = mockMvc.perform(MockMvcRequestBuilders.put("/students/1")
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
            studentRepository.save(StudentStubs.createStudent());
        }

        @Test
        void shouldDelete() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/students/1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

        }
    }
}
