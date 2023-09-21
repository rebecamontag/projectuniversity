package com.rebecamontag.projectuniversity.entity;

import jakarta.persistence.*;

@Entity
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_room_sequence")
    @SequenceGenerator(name = "class_room_sequence", sequenceName = "clsrm_seq")
    private Integer id;
    private Integer roomNumber;
    private String name;


    public ClassRoom() {
    }

    public ClassRoom(Integer id, Integer roomNumber, String name) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
