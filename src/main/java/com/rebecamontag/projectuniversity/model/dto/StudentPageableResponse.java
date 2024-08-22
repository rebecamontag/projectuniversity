package com.rebecamontag.projectuniversity.model.dto;

import java.util.List;

public record StudentPageableResponse(
        Integer totalPages,
        Integer itemsPerPage,
        Integer currentPage,
        List<StudentDTO> studentDTOList
) {
}
