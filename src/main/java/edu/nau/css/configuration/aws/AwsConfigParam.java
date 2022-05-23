package edu.nau.css.configuration.aws;

public enum AwsConfigParam {

    BUCKET("final-year-project-dev-1"),
    AWS_REGION("us-east-2");

    private String value;

    AwsConfigParam(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

}

