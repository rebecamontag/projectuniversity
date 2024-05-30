package com.rebecamontag.projectuniversity.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    @SequenceGenerator(name = "course_sequence", sequenceName = "crs_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_room_id", referencedColumnName = "id")
    private ClassRoom classRoom;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "courses")
    @ToString.Exclude
    private List<Student> students;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
