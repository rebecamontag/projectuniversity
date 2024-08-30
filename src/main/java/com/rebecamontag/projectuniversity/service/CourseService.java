package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.CourseDTO;
import com.rebecamontag.projectuniversity.model.dto.CoursePageableResponse;
import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.dto.ProfessorPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.mapper.CourseMapper;
import com.rebecamontag.projectuniversity.model.mapper.ProfessorMapper;
import com.rebecamontag.projectuniversity.repository.CourseRepository;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseDTO create(CourseDTO courseDTO) {
        courseRepository.findByName(courseDTO.name())
                .ifPresent(course -> {
                    throw new DuplicateException("Course %s already exists".formatted(course.getName()));
                });

        Course course = CourseMapper.toEntity(courseDTO);

        return CourseMapper.toDTO(courseRepository.save(course));
    }

    public CourseDTO findByName(String name) {
        return CourseMapper.toDTO(courseRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("It was not possible to find course called " + name)));
    }

    public CourseDTO findById(Integer id) {
         return CourseMapper.toDTO(findByIdOrElseThrow(id));
    }

    public CoursePageableResponse findAll(Integer page, Integer size) {
        Page<CourseDTO> courseDTOPage = courseRepository.findAll(PageRequest.of(page, size))
                .map(CourseMapper::toDTO);

        return new CoursePageableResponse(
                courseDTOPage.getTotalPages(),
                courseDTOPage.getNumberOfElements(),
                courseDTOPage.getNumber(),
                courseDTOPage.getContent());
    }

    public CourseDTO update(Integer id, CourseDTO courseDTO) {
        Course updatedCourse = findByIdOrElseThrow(id);
        updatedCourse.setName(courseDTO.name());
        updatedCourse.setDescription(courseDTO.description());

        return CourseMapper.toDTO(courseRepository.save(updatedCourse));
    }

    public void deleteById(Integer id) {
        findByIdOrElseThrow(id);
        courseRepository.deleteById(id);
    }

    private Course findByIdOrElseThrow(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found with id " + id));
    }
}
