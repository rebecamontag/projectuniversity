package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.entity.Professor;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;


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

    public void delete(Integer id) {
        professorRepository.deleteById(id);
    }
}
