package com.rebecamontag.projectuniversity.controller;

import com.rebecamontag.projectuniversity.model.dto.ClassRoomDTO;
import com.rebecamontag.projectuniversity.model.dto.ClassRoomPageableResponse;
import com.rebecamontag.projectuniversity.service.ClassRoomService;
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
@RequestMapping(value = "classrooms")
@RequiredArgsConstructor
public class ClassRoomController {


    private final ClassRoomService classRoomService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ClassRoomDTO dto) {
        ClassRoomDTO classRoomDTO = classRoomService.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(classRoomDTO.id())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/roomNumber/{roomNumber}")
    public ResponseEntity<ClassRoomDTO> findByRoomNumber(@PathVariable Integer roomNumber) {
        ClassRoomDTO classRoomDTO = classRoomService.findByRoomNumber(roomNumber);
        return ResponseEntity.ok().body(classRoomDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClassRoomDTO> findById(@PathVariable Integer id) {
        ClassRoomDTO classRoomDTO = classRoomService.findById(id);
        return ResponseEntity.ok().body(classRoomDTO);
    }

    @GetMapping
    public ResponseEntity<ClassRoomPageableResponse> findAll(@RequestParam(value="page", defaultValue="0") Integer page,
                                                             @RequestParam(value="size", defaultValue="0") Integer size) {
        ClassRoomPageableResponse classRoomDTO = classRoomService.findAll(page, size);
        return ResponseEntity.ok().body(classRoomDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClassRoomDTO> update(@PathVariable Integer id, @RequestBody ClassRoomDTO dto) {
        classRoomService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        classRoomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
