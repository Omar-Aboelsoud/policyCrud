package com.example.policycrud.exception;


import org.springframework.http.HttpStatus;
public enum ServerError {
    INTERNAL_SERVER_ERROR("internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_TO_CREATE_NEW_POLICY("Failed to create new policy"),

    FAILED_TO_UPDATE_POLICY_BY_ID("Failed to update policy by id"),


    FAILED_TO_GET_POLICY_INFORMATION("Failed to get policy information"),


    FAILED_TO_CREATE_INSURED_PERSON("Failed to create and save insured person"),

    FAILED_TO_CREATE_LIST_OF_INSURED_PERSONS("Failed to create and save insured person List"),
    POLICY_IS_NOT_EXIST("Policy is not exist"),

    FAILED_TO_GET_INSURED_PERSONS_BY_POLICY_ID("Failed to get insured persons by policy Id");


    public enum Type {
        ERROR, WARNING, INFO;
    }

    private Type type;
    private transient HttpStatus status;
    private boolean notifyDevelopers;
    private String message;

    private ServerError(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    private ServerError(String message, HttpStatus status) {
        this(message, Type.ERROR, status, false);
    }

    private ServerError(String message, Type t) {
        this(message, t, HttpStatus.BAD_REQUEST, false);
    }

    private ServerError(String message, Type t, HttpStatus status) {
        this(message, t, status, false);
    }

    private ServerError(String message, Type t, HttpStatus status, boolean notifyDevloper) {
        this.type = t;
        this.status = status;
        this.notifyDevelopers = notifyDevloper;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }
}
