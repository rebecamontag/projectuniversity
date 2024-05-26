package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.entity.Professor;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;


    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor create(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor findById(Integer id) throws BadRequestException {
        Optional<Professor> professor = professorRepository.findById(id);
        return professor.orElseThrow(() -> new BadRequestException("Professor não encontrado com o id " + id));
    }

    public Professor findByName(String name) throws BadRequestException {
        Optional<Professor> professor = professorRepository.findByName(name);
        return professor.orElseThrow(() -> new BadRequestException("Não foi possível encontrar o professor chamado " + name));
    }

    public Page<Professor> findAll(Pageable pageable) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        Page<Professor> allProfessors = professorRepository.findAll(pageRequest);

        return professorRepository.findAll(pageable);
    }

    public void update(Integer id, Professor professor) {
        Professor updatedProfessor = professorRepository.findById(id).orElseThrow();
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
