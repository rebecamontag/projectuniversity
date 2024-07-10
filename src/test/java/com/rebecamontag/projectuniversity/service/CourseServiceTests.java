package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.model.dto.CourseDTO;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.repository.CourseRepository;
import com.rebecamontag.projectuniversity.stubs.dto.CourseDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.CourseStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CourseService courseService;

    Course course;

    CourseDTO courseDTO;


    @BeforeEach
    public void setUp() {
        course = CourseStubs.createCourse1();
        courseDTO = CourseDTOStubs.createCourseDTO1();
    }

    @Nested
    class CreateTests {

        @Test
        public void shouldCreateCourseWithSuccess() {
            when(courseRepository.findByName(courseDTO.name())).thenReturn(Optional.empty());
            when(courseRepository.save(course)).thenReturn(course);

            CourseDTO expectedCourse = courseService.create(courseDTO);

            assertEquals(courseDTO, expectedCourse);
        }

        @Test
        public void shouldThrowExceptionWhenNameAlreadyExists() {
            when(courseRepository.findByName(courseDTO.name())).thenReturn(Optional.of(course));

            assertThrows(DuplicateException.class,
                    () -> courseService.create(courseDTO),
                    "Course %s already exists".formatted(courseDTO.name()));
        }
    }
}
