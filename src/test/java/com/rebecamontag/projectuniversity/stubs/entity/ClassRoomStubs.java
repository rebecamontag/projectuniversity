package com.rebecamontag.projectuniversity.stubs.entity;

import com.rebecamontag.projectuniversity.model.entity.ClassRoom;
import com.rebecamontag.projectuniversity.model.entity.Course;

import static org.mockito.ArgumentMatchers.any;

public class ClassRoomStubs {

    public static ClassRoom createClassRoom() {
        return ClassRoom.builder()
                .id(1)
                .roomNumber(10)
                .course(null)
                .name("Math Classroom")
                .build();
    }

    public static ClassRoom createClassRoom2() {
        return ClassRoom.builder()
                .id(1)
                .roomNumber(10)
                .name("Math Classroom")
                .build();
    }

    public static ClassRoom createClassRoom3() {
        return ClassRoom.builder()
                .id(2)
                .roomNumber(20)
                .name("Chemistry Classroom")
                .build();
    }
}
