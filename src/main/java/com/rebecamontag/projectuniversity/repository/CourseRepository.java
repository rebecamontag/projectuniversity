package com.rebecamontag.projectuniversity.repository;

import com.rebecamontag.projectuniversity.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Optional<Course> findByName(String name);
}
