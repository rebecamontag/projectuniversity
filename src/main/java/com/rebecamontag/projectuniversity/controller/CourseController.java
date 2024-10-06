package com.rebecamontag.projectuniversity.controller;

import com.rebecamontag.projectuniversity.model.dto.CourseDTO;
import com.rebecamontag.projectuniversity.model.dto.CoursePageableResponse;
import com.rebecamontag.projectuniversity.service.CourseService;
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
@RequestMapping(value = "courses")
@RequiredArgsConstructor
public class CourseController {


    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CourseDTO dto) {
        CourseDTO courseDTO = courseService.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(courseDTO.id())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<CourseDTO> findByName(@PathVariable String name) {
        CourseDTO courseDTO = courseService.findByName(name);
        return ResponseEntity.ok().body(courseDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable Integer id) {
        CourseDTO courseDTO = courseService.findById(id);
        return ResponseEntity.ok().body(courseDTO);
    }

    @GetMapping
    public ResponseEntity<CoursePageableResponse> findAll(@RequestParam(value="page", defaultValue="0") Integer page,
                                                          @RequestParam(value="size", defaultValue="0") Integer size) {
        CoursePageableResponse courseDTO = courseService.findAll(page, size);
        return ResponseEntity.ok().body(courseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable Integer id, @RequestBody CourseDTO dto) {
        courseService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
