package com.rebecamontag.projectuniversity.service;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import com.rebecamontag.projectuniversity.model.dto.ProfessorDTO;
import com.rebecamontag.projectuniversity.model.entity.Professor;
import com.rebecamontag.projectuniversity.model.mapper.ProfessorMapper;
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

    public ProfessorDTO create(ProfessorDTO professorDTO) {
        professorRepository.findByDocument(professorDTO.document())
                .ifPresent(professor -> {
                    throw new DuplicateException("Document %s already exists".formatted(professor.document()));
                });

        Professor professor = ProfessorMapper.toEntity(professorDTO);

        return ProfessorMapper.toDTO(professorRepository.save(professor));
    }

    public ProfessorDTO findByDocument(String document) {
        return professorRepository.findByDocument(document)
                .orElseThrow(() -> new NotFoundException("Professor not found with document number " + document));
    }

    public ProfessorDTO findById(Integer id) {
         return professorRepository.findById(id)
                 .map(ProfessorMapper::toDTO)
                 .orElseThrow(() -> new NotFoundException("Professor not found with id " + id));

    }

    public ProfessorDTO findByName(String name) {
        Optional<ProfessorDTO> professorDTO = professorRepository.findByName(name);
        return professorDTO.orElseThrow(() -> new NotFoundException("Não foi possível encontrar o professor chamado " + name));
    }

    public Page<ProfessorDTO> findAll(Integer page, Integer size) {
        return professorRepository.findAll(PageRequest.of(page, size))
                .map(ProfessorMapper::toDTO);
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
