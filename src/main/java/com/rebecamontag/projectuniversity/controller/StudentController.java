package com.rebecamontag.projectuniversity.controller;

import com.rebecamontag.projectuniversity.model.dto.StudentDTO;
import com.rebecamontag.projectuniversity.model.dto.StudentPageableResponse;
import com.rebecamontag.projectuniversity.service.StudentService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "students")
@RequiredArgsConstructor
public class StudentController {


    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody StudentDTO dto) {
        StudentDTO studentDTO = studentService.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(studentDTO.id())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/document/{document}")
    public ResponseEntity<StudentDTO> findByDocument(@PathVariable String document) {
        StudentDTO studentDTO = studentService.findByDocument(document);
        return ResponseEntity.ok().body(studentDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Integer id) {
        StudentDTO studentDTO = studentService.findById(id);
        return ResponseEntity.ok().body(studentDTO);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<StudentDTO> findByFirstName(@PathVariable String name) {
        StudentDTO studentDTO = studentService.findByFirstName(name);
        return ResponseEntity.ok().body(studentDTO);
    }

    @GetMapping
    public ResponseEntity<StudentPageableResponse> findAll(@RequestParam(value="page", defaultValue="0") Integer page,
                                                           @RequestParam(value="size", defaultValue="0") Integer size) {
        StudentPageableResponse studentDTO = studentService.findAll(page, size);
        return ResponseEntity.ok().body(studentDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable Integer id, @RequestBody StudentDTO dto) {
        studentService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
