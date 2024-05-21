package com.rebecamontag.projectuniversity.repository;

import com.rebecamontag.projectuniversity.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    Optional<Professor> findByName(String name);


}
