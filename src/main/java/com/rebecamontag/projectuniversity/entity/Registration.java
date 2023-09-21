package com.rebecamontag.projectuniversity.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_sequence")
    @SequenceGenerator(name = "registration_sequence", sequenceName = "reg_seq")
    private Integer id;
    private Student student;
    private Course course;
    private Date start;


    public Registration() {
    }

    public Registration(Integer id, Student student, Course course, Date start) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.start = start;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
