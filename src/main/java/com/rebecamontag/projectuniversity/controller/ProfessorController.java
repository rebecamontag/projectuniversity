package com.rebecamontag.projectuniversity.controller;

import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.dto.ProfessorPageableResponse;
import com.rebecamontag.projectuniversity.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "professors")
@RequiredArgsConstructor
public class ProfessorController {


    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProfessorDTO dto) {
        ProfessorDTO professorDTO = professorService.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(professorDTO.id())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{document}")
    public ResponseEntity<ProfessorDTO> findByDocument(@PathVariable String document) {
        ProfessorDTO professorDTO = professorService.findByDocument(document);
        return ResponseEntity.ok().body(professorDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProfessorDTO> findById(@PathVariable Integer id) {
        ProfessorDTO professorDTO = professorService.findById(id);
        return ResponseEntity.ok().body(professorDTO);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<ProfessorDTO> findByName(@PathVariable String name) {
        ProfessorDTO professorDTO = professorService.findByName(name);
        return ResponseEntity.ok().body(professorDTO);
    }

    @GetMapping
    public ResponseEntity<ProfessorPageableResponse> findAll(@RequestParam(value="page", defaultValue="0") Integer page,
                                                             @RequestParam(value="size", defaultValue="0") Integer size) {
        ProfessorPageableResponse professorDTO = professorService.findAll(page, size);
        return ResponseEntity.ok().body(professorDTO);
    }

    @PutMapping
    public ResponseEntity<ProfessorDTO> update(@PathVariable Integer id, @RequestBody ProfessorDTO dto) {
        professorService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        professorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
