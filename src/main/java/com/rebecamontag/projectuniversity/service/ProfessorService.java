package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public Professor create(Professor professor) {
        Professor validatedProfessor = findByDocument(professor.getDocument());
        if (null != validatedProfessor) throw new NotFoundException("Documento já existente, não foi possível criar este professor");

        return professorRepository.save(professor);
    }

    public Professor findByDocument(String document) {
        return professorRepository.findByDocument(document)
                .orElseThrow(() -> new NotFoundException("Professor not found with document number " + document));
    }

    public Professor findById(Integer id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor not found with id " + id));
    }

    public Professor findByName(String name) {
        Optional<Professor> professor = professorRepository.findByName(name);
        return professor.orElseThrow(() -> new NotFoundException("Não foi possível encontrar o professor chamado " + name));
    }

    public Page<Professor> findAll(Integer page, Integer size) {
        return professorRepository.findAll(PageRequest.of(page, size));
    }

    public void update(Integer id, Professor professor) {
        Professor updatedProfessor = findById(id);
        updatedProfessor.setFirstName(professor.getFirstName());
        updatedProfessor.setLastName(professor.getLastName());
        updatedProfessor.setBirthDate(professor.getBirthDate());
        updatedProfessor.setDocument(professor.getDocument());
        updatedProfessor.setEmail(professor.getEmail());
        updatedProfessor.setCourses(professor.getCourses());

        professorRepository.save(updatedProfessor);
    }

    public void deleteById(Integer id) {
        professorRepository.deleteById(id);
    }
}
