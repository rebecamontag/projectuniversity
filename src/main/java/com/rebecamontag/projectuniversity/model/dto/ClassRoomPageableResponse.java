package com.rebecamontag.projectuniversity.model.dto;

import java.util.List;

public record ClassRoomPageableResponse(
        Integer totalPages,
        Integer itemsPerPage,
        Integer currentPage,
        List<ClassRoomDTO> classRoomDTOList
) {
}
