package com.rebecamontag.projectuniversity;

import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.stubs.entity.CourseStubs;
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

import java.util.List;

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
                            "gender":"MALE",
                            "courses":
                                []
                                
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
            professorRepository.save(ProfessorStubs.createProfessor6());
        }

        @AfterEach
        void cleanUpDatabase() {
            professorRepository.deleteAll();
        }

        @Test
        void shouldFindByDocument() throws Exception {
            professorRepository.save(ProfessorStubs.createProfessor7());
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
                            "gender":"MALE",
                            "courses":
                                []
                                
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
            professorRepository.save(ProfessorStubs.createProfessor8());
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
                            "gender":"FEMALE",
                            "courses":
                            [{
                            "id":1,
                            "name":"Math",
                            "description":"Math lessons"
                            }]
                                
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
            professorRepository.save(ProfessorStubs.createProfessor9());
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
                            "gender":"MALE",
                            "courses":
                            [{
                            "id":1,
                            "name":"Math",
                            "description":"Math lessons"
                            }]
                                
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
            Professor professor1 = ProfessorStubs.createProfessor10();
            professor1.setId(null);
            Professor professor2 = ProfessorStubs.createProfessor3();
            professor2.setId(null);
            professorRepository.saveAll(List.of(professor1, professor2));

            Professor savedProfessor1 = professorRepository.findById(professor1.getId()).orElseThrow();
            Professor savedProfessor2 = professorRepository.findById(professor2.getId()).orElseThrow();

            Course course1 = CourseStubs.createCourse6();
            course1.setProfessor(savedProfessor1);
            Course course2 = CourseStubs.createCourse7();
            course2.setProfessor(savedProfessor2);
            courseRepository.saveAll(List.of(course1, course2));
        }

        @AfterEach
        void cleanUpDatabase() {
            professorRepository.deleteAll();
        }

        @Test
        void shouldFindAll() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/professors?page=0&size=10"))
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
                            "professorDTOList":
                                  [
                                      {
                                      "id":1,
                                      "firstName":"Matheus",
                                      "lastName":"Pusinhol",
                                      "birthDate":"2024-07-08",
                                      "document":"98765432100",
                                      "email":"teste2@gmail.com",
                                      "gender":"MALE",
                                      "courses":
                                      [
                                      {
                                      "id":1,
                                      "name":"Math",
                                      "description":"Math lessons"
                                      },
                                      {
                                      "id":2,
                                      "name":"Chemistry",
                                      "description":"Chemistry lessons"
                                      },
                                      {
                                      "id":3,
                                      "name":"Math",
                                      "description":"Math lessons"
                                      }
                                      ]
                                      },
                                  {
                                      "id":2,
                                      "firstName":"Rebeca",
                                      "lastName":"M. Pusinhol",
                                      "birthDate":"2024-07-08",
                                      "document":"12345678900",
                                      "email":"teste@gmail.com",
                                      "gender":"FEMALE",
                                      "courses":
                                      [{
                                      "id":4,
                                      "name":"Chemistry",
                                      "description":"Chemistry lessons"
                                      }]
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
                            "gender":"FEMALE",
                            "courses":
                            [{
                            "id":2,
                            "name":"Chemistry",
                            "description":"Chemistry lessons"
                            }]
                                
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
                            "gender":"FEMALE",
                            "courses":
                            [{
                            "id":2,
                            "name":"Chemistry",
                            "description":"Chemistry lessons"
                            }]
                                
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
