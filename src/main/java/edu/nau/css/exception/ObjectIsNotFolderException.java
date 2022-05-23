package edu.nau.css.exception;

import java.util.Collections;
import java.util.List;

public class ObjectIsNotFolderException extends RuntimeException {

    private final List<String> errorKeys;

    public ObjectIsNotFolderException() {
        super();
        errorKeys = Collections.emptyList();
    }

    public ObjectIsNotFolderException(String message, List<String> errorKeys) {
        super(message);
        this.errorKeys = errorKeys;
    }

    public List<String> getErrorKeys() {
        return errorKeys;
    }
}
