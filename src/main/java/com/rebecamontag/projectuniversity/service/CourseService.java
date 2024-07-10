package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.CourseDTO;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.model.mapper.CourseMapper;
import com.rebecamontag.projectuniversity.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;


    public CourseDTO create(CourseDTO courseDTO) {
        courseRepository.findByName(courseDTO.name())
                .ifPresent(course -> {
                    throw new DuplicateException("This course already exists".formatted(course.getName()));
                });

        Course course = CourseMapper.toEntity(courseDTO);

        return CourseMapper.toDTO(courseRepository.save(course));
    }

    public CourseDTO findByName(String name) {
        return CourseMapper.toDTO(courseRepository.findByName(name)
        .orElseThrow(() -> new NotFoundException("It was not possible to find course " + name)));
    }

}
