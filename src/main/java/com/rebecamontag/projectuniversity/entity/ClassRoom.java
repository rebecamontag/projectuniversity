package com.rebecamontag.projectuniversity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_room_sequence")
    @SequenceGenerator(name = "class_room_sequence", sequenceName = "clsrm_seq", allocationSize = 1)
    private Integer id;

    private Integer roomNumber;

    @OneToOne(mappedBy = "classRoom")
    private Course course;

    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return id.equals(classRoom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
