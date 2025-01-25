package com.rebecamontag.projectuniversity.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_room_sequence")
    @SequenceGenerator(name = "class_room_sequence", sequenceName = "clsrm_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer roomNumber;

    @OneToOne(mappedBy = "classRoom")
    private Course course;

    private String name;

}
