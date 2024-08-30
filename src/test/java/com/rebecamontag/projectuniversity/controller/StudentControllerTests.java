package com.rebecamontag.projectuniversity.controller;

import com.rebecamontag.projectuniversity.model.dto.StudentDTO;
import com.rebecamontag.projectuniversity.model.dto.StudentPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Student;
import com.rebecamontag.projectuniversity.service.StudentService;
import com.rebecamontag.projectuniversity.stubs.dto.StudentDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.StudentStubs;
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

@WebMvcTest(value = StudentController.class)
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    Student student;

    StudentDTO studentDTO;


    @BeforeEach
    public void setUp() {
        student = StudentStubs.createStudent();
        studentDTO = StudentDTOStubs.createStudentDTO();
    }

    @Nested
    class CreateTest {

        @Test
        void shouldCreate() throws Exception {
            when(studentService.create(studentDTO)).thenReturn(studentDTO);

            String request = """
                    {
                            "id":1,
                            "firstName":"Rebeca",
                            "lastName":"M. Pusinhol",
                            "birthDate":"2024-07-08",
                            "document":"12345678900",
                            "email":"teste@gmail.com",
                            "gender":"FEMALE"
                                
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

        @Test
        void shouldFindByDocument() throws Exception {
            when(studentService.findByDocument(studentDTO.document())).thenReturn(studentDTO);

            String result = mockMvc.perform(MockMvcRequestBuilders.get("/students/document/12345678900"))
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
    class FindByIdTest {

        @Test
        void shouldFindById() throws Exception {
            StudentDTO studentDTO2 = StudentDTOStubs.createStudentDTO2();
            when(studentService.findById(studentDTO2.id())).thenReturn(studentDTO2);

            String result = mockMvc.perform(MockMvcRequestBuilders.get("/students/2"))
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

    @Nested
    class FindByNameTest {

        @Test
        void shouldFindByFirstName() throws Exception {
            when(studentService.findByFirstName(studentDTO.firstName())).thenReturn(studentDTO);

            String result = mockMvc.perform(MockMvcRequestBuilders.get("/students/name/Rebeca"))
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
    class FindAllTest {

        @Test
        void shouldFindAll() throws Exception {
            StudentPageableResponse studentPage = new StudentPageableResponse(
                    1,
                    2,
                    0,
                    List.of(studentDTO, StudentDTOStubs.createStudentDTO2()));
            when(studentService.findAll(0, 10)).thenReturn(studentPage);

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
                                "firstName":"Rebeca",
                                "lastName":"M. Pusinhol",
                                "birthDate":"2024-07-08",
                                "document":"12345678900",
                                "email":"teste@gmail.com",
                                "gender":"FEMALE"
                                },
                            {
                                "id":2,
                                "firstName":"Matheus",
                                "lastName":"Pusinhol",
                                "birthDate":"2024-07-08",
                                "document":"98765432100",
                                "email":"teste2@gmail.com",
                                "gender":"MALE"}]}""",
                    result, JSONCompareMode.STRICT);
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void shouldUpdate() throws Exception {
            when(studentService.update(studentDTO.id(), studentDTO)).thenReturn(studentDTO);

            String request = """
                    {
                            "id":1,
                            "firstName":"Rebeca",
                            "lastName":"M. Pusinhol",
                            "birthDate":"2024-07-08",
                            "document":"12345678900",
                            "email":"teste@gmail.com",
                            "gender":"FEMALE"
                                
                    }
                    """;

//            mockMvc.perform(MockMvcRequestBuilders.post("/professors")
//                    .content(request)
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isCreated())
//                    .andExpect(MockMvcResultMatchers.header().exists("Location"))
//                    .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/professors/1"));

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
    class DeleteTest {

        @Test
        public void shouldDeleteWithSuccess() throws Exception {
            doNothing().when(studentService).deleteById(studentDTO.id());

            mockMvc.perform(MockMvcRequestBuilders.delete("/students/1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

            verify(studentService, times(1)).deleteById(studentDTO.id());
        }
    }
}
