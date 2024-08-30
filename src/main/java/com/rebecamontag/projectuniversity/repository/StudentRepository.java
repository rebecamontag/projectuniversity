package com.rebecamontag.projectuniversity.repository;

import com.rebecamontag.projectuniversity.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByFirstName(String firstName);
    Optional<Student> findByDocument(String document);

}
