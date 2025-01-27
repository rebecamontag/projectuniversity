package com.rebecamontag.projectuniversity.exception;

import java.time.Instant;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String message
) {
}
