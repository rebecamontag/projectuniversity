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

public class CourseIT extends BaseIT {

    @Nested
    class CreateTest {

        @AfterEach
        void cleanUpDatabase() {
            courseRepository.deleteAll();
        }

        @Test
        void shouldCreate() throws Exception {
            String request = """
                    {
                            "id":1,
                            "name":"Math",
                            "description":"Math lessons"                                
                    }
                    """;

            mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.header().exists("Location"))
                    .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/courses/1"));
        }
    }

    @Nested
    class FindByNameTest {

        @BeforeEach
        void setUpDatabase() {
            courseRepository.save(CourseStubs.createCourse3());
        }

        @AfterEach
        void cleanUpDatabase() {
            courseRepository.deleteAll();
        }

        @Test
        void shouldFindByName() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/name/Chemistry"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":1,
                            "name":"Chemistry",
                            "description":"Chemistry lessons"
                                
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
            courseRepository.save(CourseStubs.createCourse3());
        }

        @AfterEach
        void cleanUpDatabase() {
            courseRepository.deleteAll();
        }

        @Test
        void shouldFindById() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":1,
                            "name":"Chemistry",
                            "description":"Chemistry lessons"
                                
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
            Course course1 = CourseStubs.createCourse3();
            course1.setId(null);
            Course course2 = CourseStubs.createCourse2();
            course2.setId(null);
            courseRepository.saveAll(List.of(course1, course2));
        }

        @AfterEach
        void cleanUpDatabase() {
            courseRepository.deleteAll();
        }

        @Test
        void shouldFindAll() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/courses?page=0&size=10"))
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
                  "courseDTOList":
                        [
                            {
                            "id":1,
                            "name":"Chemistry",
                            "description":"Chemistry lessons"
                            },
                        {
                            "id":2,
                            "name":"Math",
                            "description":"Math lessons"
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
            courseRepository.save(CourseStubs.createCourse());
        }

        @AfterEach
        void cleanUpDatabase() {
            courseRepository.deleteAll();
        }

        @Test
        void shouldUpdate() throws Exception {
            String request = """
                    {
                            "id":1,
                            "name":"Math",
                            "description":"Math lessons"                            
                    }
                    """;

            String result = mockMvc.perform(MockMvcRequestBuilders.put("/courses/1")
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
                            "name":"Math",
                            "description":"Math lessons"                                
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
            courseRepository.save(CourseStubs.createCourse());
        }

        @Test
        void shouldDelete() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/courses/1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

        }
    }
}
