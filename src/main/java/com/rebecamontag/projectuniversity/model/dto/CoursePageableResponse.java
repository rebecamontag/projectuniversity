package com.rebecamontag.projectuniversity.model.dto;

import java.util.List;

public record CoursePageableResponse(
        Integer totalPages,
        Integer itemsPerPage,
        Integer currentPage,
        List<CourseDTO> courseDTOList
) {
}
