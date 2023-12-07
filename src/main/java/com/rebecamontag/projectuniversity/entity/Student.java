package com.rebecamontag.projectuniversity.entity;

import com.rebecamontag.projectuniversity.enumeration.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @SequenceGenerator(name = "student_sequence", sequenceName = "std_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String document;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany
    @JoinTable(name = "registration",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
