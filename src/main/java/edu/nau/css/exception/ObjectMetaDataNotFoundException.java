package edu.nau.css.exception;

import java.util.Collections;
import java.util.List;

public class ObjectMetaDataNotFoundException extends RuntimeException {

    private final List<String> errorKeys;

    public ObjectMetaDataNotFoundException() {
        super();
        errorKeys = Collections.emptyList();
    }

    public ObjectMetaDataNotFoundException(String message, List<String> errorKeys) {
        super(message);
        this.errorKeys = errorKeys;
    }

    public List<String> getErrorKeys() {
        return errorKeys;
    }
}
