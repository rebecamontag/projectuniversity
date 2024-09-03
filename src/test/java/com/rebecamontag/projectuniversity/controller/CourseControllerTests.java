package com.rebecamontag.projectuniversity.controller;

import com.rebecamontag.projectuniversity.model.dto.CourseDTO;
import com.rebecamontag.projectuniversity.model.dto.CoursePageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.service.CourseService;
import com.rebecamontag.projectuniversity.stubs.dto.CourseDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.CourseStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(value = CourseController.class)
public class CourseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    Course course;

    CourseDTO courseDTO;


    @BeforeEach
    public void setUp() {
        course = CourseStubs.createCourse();
        courseDTO = CourseDTOStubs.createCourseDTO();
    }

    @Nested
    class CreateTest {

        @Test
        void shouldCreate() throws Exception {
            when(courseService.create(courseDTO)).thenReturn(courseDTO);

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

        @Test
        void shouldFindByName() throws Exception {
            when(courseService.findByName(courseDTO.name())).thenReturn(courseDTO);

            String result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/name/Math"))
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
    class FindByIdTest {

        @Test
        void shouldFindById() throws Exception {
            CourseDTO courseDTO2 = CourseDTOStubs.createCourseDTO2();
            when(courseService.findById(courseDTO2.id())).thenReturn(courseDTO2);

            String result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/2"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":2,
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

        @Test
        void shouldFindAll() throws Exception {
            CoursePageableResponse coursePage = new CoursePageableResponse(
                    1,
                    2,
                    0,
                    List.of(courseDTO, CourseDTOStubs.createCourseDTO2()));
            when(courseService.findAll(0, 10)).thenReturn(coursePage);

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
                                "name":"Math",
                                "description":"Math lessons"
                                },
                            {
                                "id":2,
                                "name":"Chemistry",
                                "description":"Chemistry lessons"
                            }]}""",
                    result, JSONCompareMode.STRICT);
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void shouldUpdate() throws Exception {
            when(courseService.update(courseDTO.id(), courseDTO)).thenReturn(courseDTO);

            String request = """
                    {
                            "id":1,
                            "name":"Math",
                            "description":"Math lessons and tests"
                    }
                    """;

//            mockMvc.perform(MockMvcRequestBuilders.post("/professors")
//                    .content(request)
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isCreated())
//                    .andExpect(MockMvcResultMatchers.header().exists("Location"))
//                    .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/professors/1"));

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
                            "description":"Math lessons and tests"
                    }
                    """,
                    result,
                    JSONCompareMode.STRICT);

        }

    }

    @Nested
    class DeleteTest {

        @Test
        public void shouldDeleteWithSuccess() throws Exception {
            doNothing().when(courseService).deleteById(courseDTO.id());

            mockMvc.perform(MockMvcRequestBuilders.delete("/courses/1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

            verify(courseService, times(1)).deleteById(courseDTO.id());
        }
    }
}
