package com.example.policycrud.exception;

public class ErrorDto {

    private String message;

    private String developerMessage;

    private String code;

    private String clientMsg;

    private ServerError.Type type;

    private int httpStatus;

    private String uniqueIdentifier;

    private Object[] params;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ServerError.Type getType() {
        return type;
    }

    public void setType(ServerError.Type type) {
        this.type = type;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getClientMsg() {
        return clientMsg;
    }

    public void setClientMsg(String clientMsg) {
        this.clientMsg = clientMsg;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "[message=" + message + ", developerMessage=" + developerMessage + ", code=" + code + ", type="
                + type + ", httpStatus=" + httpStatus + ", uniqueIdentifier=" + uniqueIdentifier + "]";
    }
}
