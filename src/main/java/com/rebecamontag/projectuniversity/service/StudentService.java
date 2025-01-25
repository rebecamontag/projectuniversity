package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.StudentDTO;
import com.rebecamontag.projectuniversity.model.dto.StudentPageableResponse;
import com.rebecamontag.projectuniversity.model.entity.Course;
import com.rebecamontag.projectuniversity.model.entity.Student;
import com.rebecamontag.projectuniversity.model.mapper.StudentMapper;
import com.rebecamontag.projectuniversity.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    public StudentDTO create(StudentDTO studentDTO) {
        studentRepository.findByDocument(studentDTO.document())
                .ifPresent(student -> {
                    throw new DuplicateException("Document %s already exists".formatted(student.getDocument()));
                });

        Student student = StudentMapper.toEntity(studentDTO);

        return StudentMapper.toDTO(studentRepository.save(student));
    }

    public StudentDTO findByDocument(String document) {
        return StudentMapper.toDTO(studentRepository.findByDocument(document)
                .orElseThrow(() -> new NotFoundException("Student not found with document number " + document)));
    }

    public StudentDTO findById(Integer id) {
         return StudentMapper.toDTO(findByIdOrElseThrow(id));
    }

    public StudentDTO findByFirstName(String firstName) {
        return StudentMapper.toDTO(studentRepository.findByFirstName(firstName)
                .orElseThrow(() -> new NotFoundException("It was not possible to find student called " + firstName)));
    }

    public StudentPageableResponse findAll(Integer page, Integer size) {
        Page<StudentDTO> studentDTOPage = studentRepository.findAll(PageRequest.of(page, size))
                .map(StudentMapper::toDTO);

        return new StudentPageableResponse(
                studentDTOPage.getTotalPages(),
                studentDTOPage.getNumberOfElements(),
                studentDTOPage.getNumber(),
                studentDTOPage.getContent());
    }

    public StudentDTO update(Integer id, StudentDTO studentDTO) {
        Student updatedStudent = findByIdOrElseThrow(id);
        updatedStudent.setFirstName(studentDTO.firstName());
        updatedStudent.setLastName(studentDTO.lastName());
        updatedStudent.setBirthDate(studentDTO.birthDate());
        updatedStudent.setDocument(studentDTO.document());
        updatedStudent.setEmail(studentDTO.email());

        return StudentMapper.toDTO(studentRepository.save(updatedStudent));
    }

    public void deleteById(Integer id) {
        findByIdOrElseThrow(id);
        studentRepository.deleteById(id);
    }

    private Student findByIdOrElseThrow(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with id " + id));
    }

    public StudentDTO addCourseToStudent(Integer studentId, List<Integer> courseIds) {
        Student student = findByIdOrElseThrow(studentId);
        List<Course> courseList = courseIds.stream()
                .map(courseService::findByIdOrElseThrow)
                .collect(Collectors.toList());
        student.setCourses(courseList);
        return StudentMapper.toDTO(student);
    }

    public void deleteCourseFromStudent(Integer studentId, Integer courseId) {
        Student student = findByIdOrElseThrow(studentId);
        Course courseToRemove = courseService.findByIdOrElseThrow(courseId);
        List<Course> courseList = student.getCourses();
        List<Course> updatedList = courseList.stream()
                .filter(c -> !c.getId().equals(courseToRemove.getId()))
                .collect(Collectors.toList());
        student.setCourses(updatedList);
    }
}
