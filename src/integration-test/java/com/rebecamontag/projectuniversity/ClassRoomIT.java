package com.rebecamontag.projectuniversity;

import com.rebecamontag.projectuniversity.model.entity.ClassRoom;
import com.rebecamontag.projectuniversity.model.entity.Student;
import com.rebecamontag.projectuniversity.stubs.entity.ClassRoomStubs;
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

public class ClassRoomIT extends BaseIT {

    @Nested
    class CreateTest {

        @AfterEach
        void cleanUpDatabase() {
            classRoomRepository.deleteAll();
        }

        @Test
        void shouldCreate() throws Exception {
            String request = """
                    {
                            "id":1,
                            "roomNumber":20,
                            "name":"Chemistry Classroom"                                
                    }
                    """;

            mockMvc.perform(MockMvcRequestBuilders.post("/classrooms")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.header().exists("Location"))
                    .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/classrooms/1"));
        }
    }

    @Nested
    class FindByRoomNumberTest {

        @BeforeEach
        void setUpDatabase() {
            classRoomRepository.save(ClassRoomStubs.createClassRoom3());
        }

        @AfterEach
        void cleanUpDatabase() {
            classRoomRepository.deleteAll();
        }

        @Test
        void shouldFindByRoomNumber() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/classrooms/roomNumber/20"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":1,
                            "roomNumber":20,
                            "name":"Chemistry Classroom"                                
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
            classRoomRepository.save(ClassRoomStubs.createClassRoom3());
        }

        @AfterEach
        void cleanUpDatabase() {
            classRoomRepository.deleteAll();
        }

        @Test
        void shouldFindById() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/classrooms/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":1,
                            "roomNumber":20,
                            "name":"Chemistry Classroom"
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
            ClassRoom classRoom1 = ClassRoomStubs.createClassRoom3();
            classRoom1.setId(null);
            ClassRoom classRoom2 = ClassRoomStubs.createClassRoom2();
            classRoom2.setId(null);
            classRoomRepository.saveAll(List.of(classRoom1, classRoom2));
        }

        @AfterEach
        void cleanUpDatabase() {
            classRoomRepository.deleteAll();
        }

        @Test
        void shouldFindAll() throws Exception {
            String result = mockMvc.perform(MockMvcRequestBuilders.get("/classrooms?page=0&size=10"))
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
                  "classRoomDTOList":
                        [
                            {
                            "id":1,
                            "roomNumber":20,
                            "name":"Chemistry Classroom"
                            },
                        {
                            "id":2,
                            "roomNumber":10,
                            "name":"Math Classroom"
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
            classRoomRepository.save(ClassRoomStubs.createClassRoom());
        }

        @AfterEach
        void cleanUpDatabase() {
            classRoomRepository.deleteAll();
        }

        @Test
        void shouldUpdate() throws Exception {
            String request = """
                    {
                            "id":1,
                            "roomNumber":10,
                            "name":"Chemistry Classroom"
                    }
                    """;

            String result = mockMvc.perform(MockMvcRequestBuilders.put("/classrooms/1")
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
                            "roomNumber":10,
                            "name":"Chemistry Classroom"
                                
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
            classRoomRepository.save(ClassRoomStubs.createClassRoom());
        }

        @Test
        void shouldDelete() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/classrooms/1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

        }
    }
}
