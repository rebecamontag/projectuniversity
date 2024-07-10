package com.rebecamontag.projectuniversity.model.dto;

import java.util.List;

public record ProfessorPageableResponse (
        Integer totalPages,
        Integer itemsPerPage,
        Integer currentPage,
        List<ProfessorDTO> professorDTOList
) {
}
