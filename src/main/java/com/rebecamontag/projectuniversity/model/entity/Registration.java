package com.rebecamontag.projectuniversity.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_sequence")
    @SequenceGenerator(name = "registration_sequence", sequenceName = "reg_seq", allocationSize = 1)
    private Integer id;

    private Student student;

    private Course course;

    private LocalDate start;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registration that = (Registration) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
