package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.ClassRoomDTO;
import com.rebecamontag.projectuniversity.model.dto.ClassRoomPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.ClassRoom;
import com.rebecamontag.projectuniversity.model.mapper.ClassRoomMapper;
import com.rebecamontag.projectuniversity.repository.ClassRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;

    public ClassRoomDTO create(ClassRoomDTO classRoomDTO) {
        classRoomRepository.findByRoomNumber(classRoomDTO.roomNumber())
                .ifPresent(classRoom -> {
                    throw new DuplicateException("Classroom number %s already exists".formatted(classRoom.getRoomNumber()));
                });

        ClassRoom classRoom = ClassRoomMapper.toEntity(classRoomDTO);

        return ClassRoomMapper.toDTO(classRoomRepository.save(classRoom));
    }

    public ClassRoomDTO findByRoomNumber(Integer roomNumber) {
        return ClassRoomMapper.toDTO(classRoomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new NotFoundException("Classroom not found with number " + roomNumber)));
    }

    public ClassRoomDTO findById(Integer id) {
         return ClassRoomMapper.toDTO(findByIdOrElseThrow(id));
    }

    public ClassRoomPageableResponse findAll(Integer page, Integer size) {
        Page<ClassRoomDTO> classRoomDTOPage = classRoomRepository.findAll(PageRequest.of(page, size))
                .map(ClassRoomMapper::toDTO);

        return new ClassRoomPageableResponse(
                classRoomDTOPage.getTotalPages(),
                classRoomDTOPage.getNumberOfElements(),
                classRoomDTOPage.getNumber(),
                classRoomDTOPage.getContent());
    }

    public ClassRoomDTO update(Integer id, ClassRoomDTO classRoomDTO) {
        ClassRoom updatedClassRoom = findByIdOrElseThrow(id);
        updatedClassRoom.setRoomNumber(classRoomDTO.roomNumber());
        updatedClassRoom.setName(classRoomDTO.name());

        return ClassRoomMapper.toDTO(classRoomRepository.save(updatedClassRoom));
    }

    public void deleteById(Integer id) {
        findByIdOrElseThrow(id);
        classRoomRepository.deleteById(id);
    }

    private ClassRoom findByIdOrElseThrow(Integer id) {
        return classRoomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Classroom not found with id " + id));
    }
}
