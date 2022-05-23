package edu.nau.css.aws.exception;

public class BucketExistsException extends RuntimeException {

    public BucketExistsException(String message) {
        super(message);
    }

    public BucketExistsException() {

    }
}
