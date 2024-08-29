package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.ClassRoomDTO;
import com.rebecamontag.projectuniversity.model.dto.ClassRoomPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.ClassRoom;
import com.rebecamontag.projectuniversity.repository.ClassRoomRepository;
import com.rebecamontag.projectuniversity.stubs.dto.ClassRoomDTOStubs;
import com.rebecamontag.projectuniversity.stubs.entity.ClassRoomStubs;
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
public class ClassRoomServiceTests {

    @Mock
    ClassRoomRepository classRoomRepository;

    @InjectMocks
    ClassRoomService classRoomService;

    ClassRoom classRoom;

    ClassRoomDTO classRoomDTO;


    @BeforeEach
    public void setUp() {
        classRoom = ClassRoomStubs.createClassRoom();
        classRoomDTO = ClassRoomDTOStubs.createClassRoomDTO();
    }

    @Nested
    class CreateTests {

        @Test
        public void shouldCreateClassRoomWithSuccess() {
            when(classRoomRepository.findByRoomNumber(classRoomDTO.roomNumber())).thenReturn(Optional.empty());
            when(classRoomRepository.save(classRoom)).thenReturn(classRoom);

            ClassRoomDTO expectedClassRoom = classRoomService.create(classRoomDTO);

            assertEquals(classRoomDTO, expectedClassRoom);
        }

        @Test
        public void shouldThrowExceptionWhenRoomNumberAlreadyExist() {
            when(classRoomRepository.findByRoomNumber(classRoomDTO.roomNumber())).thenReturn(Optional.of(classRoom));

            assertThrows(DuplicateException.class,
                    () -> classRoomService.create(classRoomDTO),
                    "Classroom number %s already exists".formatted(classRoomDTO.roomNumber()));
        }
    }

    @Nested
    class FindByRoomNumberTests {

        @Test
        public void shouldFindByRoomNumberWithSuccess() {
            when(classRoomRepository.findByRoomNumber(classRoomDTO.roomNumber())).thenReturn(Optional.of(classRoom));

            ClassRoomDTO expectedClassRoom = classRoomService.findByRoomNumber(classRoomDTO.roomNumber());

            assertEquals(classRoomDTO, expectedClassRoom);
            verify(classRoomRepository, times(1)).findByRoomNumber(classRoomDTO.roomNumber());
            verifyNoMoreInteractions(classRoomRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidRoomNumber() {
            when(classRoomRepository.findByRoomNumber(classRoomDTO.roomNumber())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> classRoomService.findByRoomNumber(classRoomDTO.roomNumber()),
                    "Classroom not found with number ".concat(classRoomDTO.roomNumber().toString()));
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        public void shouldFindByIdWithSuccess() {
            when(classRoomRepository.findById(classRoomDTO.id())).thenReturn(Optional.of(classRoom));

            ClassRoomDTO expectedClassRoom = classRoomService.findById(classRoomDTO.id());

            assertEquals(classRoomDTO, expectedClassRoom);
            verify(classRoomRepository, times(1)).findById(classRoomDTO.id());
            verifyNoMoreInteractions(classRoomRepository);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidId() {
            when(classRoomRepository.findById(classRoomDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> classRoomService.findById(classRoomDTO.id()),
                    "Classroom not found with id ".concat(classRoomDTO.id().toString()));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        public void shouldFindAllWithSuccess() {
            ClassRoomDTO dto = ClassRoomDTOStubs.createClassRoomDTO3();

            ClassRoom classRoom1 = ClassRoomStubs.createClassRoom2();

            Page<ClassRoom> classRoomPage = new PageImpl<>(List.of(classRoom, classRoom1));

            when(classRoomRepository.findAll(PageRequest.of(1, 2))).thenReturn(classRoomPage);

            ClassRoomPageableResponse dtoPage = classRoomService.findAll(1, 2);

            assertNotNull(dtoPage);
            assertEquals(1, dtoPage.totalPages());
            assertEquals(2, dtoPage.itemsPerPage());
            assertEquals(0, dtoPage.currentPage());
            assertEquals(2, dtoPage.classRoomDTOList().size());
            dtoPage.classRoomDTOList().stream()
                    .filter(classRoomDTO1 -> classRoomDTO1.id().equals(classRoom.getId()))
                    .findFirst()
                    .ifPresentOrElse(classRoomDTO1 -> {
                        assertEquals(classRoom.getRoomNumber(), classRoomDTO1.roomNumber());
                        assertEquals(classRoom.getName(), classRoomDTO1.name());
                    }, Assertions::fail);
            dtoPage.classRoomDTOList().stream()
                    .filter(classRoomDTO1 -> classRoomDTO1.id().equals(classRoom1.getId()))
                    .findFirst()
                    .ifPresentOrElse(classRoomDTO1 -> {
                        assertEquals(classRoom1.getRoomNumber(), classRoomDTO1.roomNumber());
                        assertEquals(classRoom1.getName(), classRoomDTO1.name());
                    }, Assertions::fail);

            assertThat(dtoPage.classRoomDTOList()).containsExactlyInAnyOrder(classRoomDTO, dto);

        }
    }

    @Nested
    class UpdateTests {

        @Test
        public void shouldUpdateClassRoomDataWithSuccess() {
            when(classRoomRepository.findById(classRoomDTO.id())).thenReturn(Optional.of(classRoom));
            when(classRoomRepository.save(classRoom)).thenReturn(classRoom);

            ClassRoomDTO dto = new ClassRoomDTO(1,
                    10,
                    "Math Classroom");

            ClassRoomDTO classRoomDTO = classRoomService.update(dto.id(), dto);

            assertNotNull(classRoomDTO);
            assertEquals(dto.roomNumber(), classRoomDTO.roomNumber());
            assertEquals(dto.name(), classRoomDTO.name());

            verify(classRoomRepository, times(1)).save(classRoom);
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToUpdate() {
            when(classRoomRepository.findById(classRoomDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> classRoomService.update(classRoomDTO.id(), classRoomDTO),
                    "Classroom not found with id ".concat(classRoomDTO.id().toString()));
        }
    }

    @Nested
    class DeleteTests {

        @Test
        public void shouldDeleteWithSuccess() {
            when(classRoomRepository.findById(classRoomDTO.id())).thenReturn(Optional.of(classRoom));
            doNothing().when(classRoomRepository).deleteById(classRoomDTO.id());

            classRoomService.deleteById(classRoomDTO.id());

            verify(classRoomRepository, times(1)).deleteById(classRoomDTO.id());
        }

        @Test
        public void shouldThrowExceptionWhenNotValidIdToDelete() {
            when(classRoomRepository.findById(classRoomDTO.id())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> classRoomService.deleteById(classRoomDTO.id()),
                    "Classroom not found with id ".concat(classRoomDTO.id().toString()));
        }
    }
}
