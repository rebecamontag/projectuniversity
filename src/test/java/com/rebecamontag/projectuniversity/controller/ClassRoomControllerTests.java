package com.rebecamontag.projectuniversity.controller;

import com.rebecamontag.projectuniversity.model.dto.ClassRoomDTO;
import com.rebecamontag.projectuniversity.model.dto.ClassRoomPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.ClassRoom;
import com.rebecamontag.projectuniversity.service.ClassRoomService;
import com.rebecamontag.projectuniversity.stubs.dto.ClassRoomDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.ClassRoomStubs;
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

@WebMvcTest(value = ClassRoomController.class)
public class ClassRoomControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassRoomService classRoomService;

    ClassRoom classRoom;

    ClassRoomDTO classRoomDTO;


    @BeforeEach
    public void setUp() {
        classRoom = ClassRoomStubs.createClassRoom();
        classRoomDTO = ClassRoomDTOStubs.createClassRoomDTO();
    }

    @Nested
    class CreateTest {

        @Test
        void shouldCreate() throws Exception {
            when(classRoomService.create(classRoomDTO)).thenReturn(classRoomDTO);

            String request = """
                    {
                            "id":1,
                            "roomNumber":"10",
                            "name":"Math Classroom"                                
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

        @Test
        void shouldFindByRoomNumber() throws Exception {
            when(classRoomService.findByRoomNumber(classRoomDTO.roomNumber())).thenReturn(classRoomDTO);

            String result = mockMvc.perform(MockMvcRequestBuilders.get("/classrooms/roomNumber/10"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":1,
                            "roomNumber":10,
                            "name":"Math Classroom"                                
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
            ClassRoomDTO classRoomDTO2 = ClassRoomDTOStubs.createClassRoomDTO2();
            when(classRoomService.findById(classRoomDTO2.id())).thenReturn(classRoomDTO2);

            String result = mockMvc.perform(MockMvcRequestBuilders.get("/classrooms/2"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertNotNull(result);
            JSONAssert.assertEquals("""
                    {
                            "id":2,
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

        @Test
        void shouldFindAll() throws Exception {
            ClassRoomPageableResponse classRoomPage = new ClassRoomPageableResponse(
                    1,
                    2,
                    0,
                    List.of(classRoomDTO, ClassRoomDTOStubs.createClassRoomDTO2()));
            when(classRoomService.findAll(0, 10)).thenReturn(classRoomPage);

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
                            "roomNumber":10,
                            "name":"Math Classroom"
                                },
                            {
                            "id":2,
                            "roomNumber":20,
                            "name":"Chemistry Classroom"}]}""",
                    result, JSONCompareMode.STRICT);
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void shouldUpdate() throws Exception {
            when(classRoomService.update(classRoomDTO.id(), classRoomDTO)).thenReturn(classRoomDTO);

            String request = """
                    {
                            "id":1,
                            "roomNumber":10,
                            "name":"Math Classroom"                                
                    }
                    """;

//            mockMvc.perform(MockMvcRequestBuilders.post("/professors")
//                    .content(request)
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isCreated())
//                    .andExpect(MockMvcResultMatchers.header().exists("Location"))
//                    .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/professors/1"));

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
                            "name":"Math Classroom"
                                
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
            doNothing().when(classRoomService).deleteById(classRoomDTO.id());

            mockMvc.perform(MockMvcRequestBuilders.delete("/classrooms/1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

            verify(classRoomService, times(1)).deleteById(classRoomDTO.id());
        }
    }
}
