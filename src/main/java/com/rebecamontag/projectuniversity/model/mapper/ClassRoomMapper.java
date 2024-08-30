package com.rebecamontag.projectuniversity.model.mapper;

import com.rebecamontag.projectuniversity.model.dto.ClassRoomDTO;
import com.rebecamontag.projectuniversity.model.entity.ClassRoom;

public class ClassRoomMapper {

    public static ClassRoom toEntity(ClassRoomDTO classRoomDTO) {
        return ClassRoom.builder()
                .id(classRoomDTO.id())
                .roomNumber(classRoomDTO.roomNumber())
                .name(classRoomDTO.name())
                .build();
    }

    public static ClassRoomDTO toDTO(ClassRoom classRoom) {
        return new ClassRoomDTO(
                classRoom.getId(),
                classRoom.getRoomNumber(),
                classRoom.getName());
    }
}
