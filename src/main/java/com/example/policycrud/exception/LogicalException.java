package com.example.policycrud.exception;

import lombok.Data;

public class LogicalException extends BaseException{

    private static final long serialVersionUID = -6527073160488586546L;

    public LogicalException(ServerError err) {
        this(err, err.getMessage());
    }

    public LogicalException(ServerError err, String msg) {
        super(err, msg, null);
    }

    public LogicalException(ServerError err, String msg, String clientMsg) {
        super(err, msg, clientMsg);
    }

    public LogicalException(ServerError err, String msg, String clientMsg, String developer) {
        super(err, msg, clientMsg);
    }

    public LogicalException apply(Object... params) {
        this.params = params;
        return this;
    }
}
