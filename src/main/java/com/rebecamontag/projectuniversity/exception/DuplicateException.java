package com.rebecamontag.projectuniversity.exception;

public class DuplicateException extends RuntimeException {

    public DuplicateException(String msg) {
        super(msg);
    }

    public DuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}