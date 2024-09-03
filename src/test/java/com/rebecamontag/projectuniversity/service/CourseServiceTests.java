package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.CourseDTO;
import com.rebecamontag.projectuniversity.model.dto.CoursePageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.repository.CourseRepository;
import com.rebecamontag.projectuniversity.stubs.dto.CourseDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.CourseStubs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
        course = CourseStubs.createCourse();
        courseDTO = CourseDTOStubs.createCourseDTO();
    }

    @Nested
    class CreateTests {

        @Test
        public void shouldCreateProfessorWithSuccess() {
            when(courseRepository.findByName(courseDTO.name())).thenReturn(Optional.empty());
            when(courseRepository.save(course)).thenReturn(course);

            CourseDTO expectedCourse = courseService.create(courseDTO);

            assertEquals(courseDTO, expectedCourse);
        }

        @Test
        public void shouldThrowExceptionWhenDocumentAlreadyExist() {
            when(courseRepository.findByName(courseDTO.name())).thenReturn(Optional.of(course));

            assertThrows(DuplicateException.class,
                    () -> courseService.create(courseDTO),
                    "Course %s already exists".formatted(courseDTO.name()));
        }
    }

    @Nested
    class FindByNameTests {

        @Test
        public void shouldFindByFirstNameWithSuccess() {
            when(courseRepository.findByName(courseDTO.name())).thenReturn(Optional.of(course));

            CourseDTO expectedCourse = courseService.findByName(courseDTO.name());

            assertEquals(courseDTO, expectedCourse);
            verify(courseRepository, times(1)).findByName(courseDTO.name());
            verifyNoMoreInteractions(courseRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidName() {
            when(courseRepository.findByName(courseDTO.name())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> courseService.findByName(courseDTO.name()),
                    "It was not possible to find course called ".concat(courseDTO.name()));
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        public void shouldFindByIdWithSuccess() {
            when(courseRepository.findById(courseDTO.id())).thenReturn(Optional.of(course));

            CourseDTO expectedCourse = courseService.findById(courseDTO.id());

            assertEquals(courseDTO, expectedCourse);
            verify(courseRepository, times(1)).findById(courseDTO.id());
            verifyNoMoreInteractions(courseRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidId() {
            when(courseRepository.findById(courseDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> courseService.findById(courseDTO.id()),
                    "Course not found with id ".concat(courseDTO.id().toString()));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        public void shouldFindAllWithSuccess() {
            CourseDTO dto = CourseDTOStubs.createCourseDTO3();
            Course course1 = CourseStubs.createCourse2();
            Page<Course> coursePage = new PageImpl<>(List.of(course, course1));

            when(courseRepository.findAll(PageRequest.of(1, 2))).thenReturn(coursePage);

            CoursePageableResponse dtoPage = courseService.findAll(1, 2);

            assertNotNull(dtoPage);
            assertEquals(1, dtoPage.totalPages());
            assertEquals(2, dtoPage.itemsPerPage());
            assertEquals(0, dtoPage.currentPage());
            assertEquals(2, dtoPage.courseDTOList().size());
            dtoPage.courseDTOList().stream()
                    .filter(courseDTO -> courseDTO.id().equals(course.getId()))
                    .findFirst()
                    .ifPresentOrElse(courseDTO -> {
                        assertEquals(course.getName(), courseDTO.name());
                        assertEquals(course.getDescription(), courseDTO.description());
                    }, Assertions::fail);
            dtoPage.courseDTOList().stream()
                    .filter(courseDTO1 -> courseDTO1.id().equals(course1.getId()))
                    .findFirst()
                    .ifPresentOrElse(courseDTO1 -> {
                        assertEquals(course1.getName(), courseDTO1.name());
                        assertEquals(course1.getDescription(), courseDTO1.description());
                    }, Assertions::fail);

            assertThat(dtoPage.courseDTOList()).containsExactlyInAnyOrder(courseDTO, dto);

        }
    }

    @Nested
    class UpdateTests {

        @Test
        public void shouldUpdateProfessorDataWithSuccess() {
            when(courseRepository.findById(courseDTO.id())).thenReturn(Optional.of(course));
            when(courseRepository.save(course)).thenReturn(course);

            CourseDTO dto = new CourseDTO(1,
                    "Math",
                    "Math lessons"
            );

            CourseDTO courseDTO = courseService.update(dto.id(), dto);

            assertNotNull(courseDTO);
            assertEquals(dto.name(), courseDTO.name());
            assertEquals(dto.description(), courseDTO.description());

            verify(courseRepository, times(1)).save(course);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToUpdate() {
            when(courseRepository.findById(courseDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> courseService.update(courseDTO.id(), courseDTO),
                    "Course not found with id ".concat(courseDTO.id().toString()));
        }
    }

    @Nested
    class DeleteTests {

        @Test
        public void shouldDeleteWithSuccess() {
            when(courseRepository.findById(courseDTO.id())).thenReturn(Optional.of(course));
            doNothing().when(courseRepository).deleteById(courseDTO.id());

            courseService.deleteById(courseDTO.id());

            verify(courseRepository, times(1)).deleteById(courseDTO.id());
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToDelete() {
            when(courseRepository.findById(courseDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> courseService.deleteById(courseDTO.id()),
                    "Course not found with id ".concat(courseDTO.id().toString()));
        }
    }
}
