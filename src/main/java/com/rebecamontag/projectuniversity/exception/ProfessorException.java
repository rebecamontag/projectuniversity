package com.rebecamontag.projectuniversity.exception;

public class ProfessorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProfessorException(String msg) {
        super(msg);
    }

    public ProfessorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
