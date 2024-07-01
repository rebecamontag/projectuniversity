package com.rebecamontag.projectuniversity.controller;

import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.mapper.ProfessorMapper;
import com.rebecamontag.projectuniversity.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "professor")
@RequiredArgsConstructor
public class ProfessorController {


    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProfessorDTO professorDTO) {
        professorService.create(professorDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/{document}")
    public ResponseEntity<Professor> findByDocument(@PathVariable String document) {
        Professor professor = ProfessorMapper.toEntity(professorService.findByDocument(document));

        return ResponseEntity.ok().body(professor);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Professor> findById(@PathVariable Integer id) {
        Professor professor = ProfessorMapper.toEntity(professorService.findById(id));

        return ResponseEntity.ok().body(professor);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<Professor> findByName(@PathVariable String name) {
        Professor professor = ProfessorMapper.toEntity(professorService.findByName(name));

        return ResponseEntity.ok().body(professor);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ProfessorDTO professorDTO) {
        professorService.update(id, professorDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        professorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
