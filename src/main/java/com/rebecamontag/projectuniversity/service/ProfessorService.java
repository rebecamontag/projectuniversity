package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.dto.ProfessorPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.mapper.CourseMapper;
import com.rebecamontag.projectuniversity.model.mapper.ProfessorMapper;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final CourseService courseService;

    public ProfessorDTO create(ProfessorDTO professorDTO) {
        professorRepository.findByDocument(professorDTO.document())
                .ifPresent(professor -> {
                    throw new DuplicateException("Document %s already exists".formatted(professor.getDocument()));
                });

        Professor professor = ProfessorMapper.toEntity(professorDTO);

        return ProfessorMapper.toDTO(professorRepository.save(professor));
    }

    public ProfessorDTO findByDocument(String document) {
        return ProfessorMapper.toDTO(professorRepository.findByDocument(document)
                .orElseThrow(() -> new NotFoundException("Professor not found with document number " + document)));
    }

    public ProfessorDTO findById(Integer id) {
         return ProfessorMapper.toDTO(findByIdOrElseThrow(id));
    }

    public ProfessorDTO findByFirstName(String name) {
        return ProfessorMapper.toDTO(professorRepository.findByFirstName(name)
                .orElseThrow(() -> new NotFoundException("It was not possible to find professor called " + name)));
    }

    public ProfessorPageableResponse findAll(Integer page, Integer size) {
        Page<ProfessorDTO> professorDTOPage = professorRepository.findAll(PageRequest.of(page, size))
                .map(ProfessorMapper::toDTO);

        return new ProfessorPageableResponse(
                professorDTOPage.getTotalPages(),
                professorDTOPage.getNumberOfElements(),
                professorDTOPage.getNumber(),
                professorDTOPage.getContent());
    }

    public ProfessorDTO update(Integer id, ProfessorDTO professorDTO) {
        Professor updatedProfessor = findByIdOrElseThrow(id);
        updatedProfessor.setFirstName(professorDTO.firstName());
        updatedProfessor.setLastName(professorDTO.lastName());
        updatedProfessor.setBirthDate(professorDTO.birthDate());
        updatedProfessor.setDocument(professorDTO.document());
        updatedProfessor.setEmail(professorDTO.email());

        return ProfessorMapper.toDTO(professorRepository.save(updatedProfessor));
    }

    public void deleteById(Integer id) {
        findByIdOrElseThrow(id);
        professorRepository.deleteById(id);
    }

    private Professor findByIdOrElseThrow(Integer id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor not found with id " + id));
    }

    public ProfessorDTO addCourseToProfessor(Integer professorId, List<Integer> courseIds) {
        Professor professor = findByIdOrElseThrow(professorId);
        List<Course> courseList = courseIds.stream()
                .map(courseService::findByIdOrElseThrow)
                .collect(Collectors.toList());
        professor.setCourses(courseList);
        return ProfessorMapper.toDTO(professor);
    }

    public void deleteCourseFromProfessor(Integer professorId, Integer courseId) {
        Professor professor = findByIdOrElseThrow(professorId);
        Course courseToRemove = courseService.findByIdOrElseThrow(courseId);
        List<Course> courseList = professor.getCourses();
        List<Course> updatedList = courseList.stream()
                .filter(c -> !c.getId().equals(courseToRemove.getId()))
                .collect(Collectors.toList());
        professor.setCourses(updatedList);
    }
}
