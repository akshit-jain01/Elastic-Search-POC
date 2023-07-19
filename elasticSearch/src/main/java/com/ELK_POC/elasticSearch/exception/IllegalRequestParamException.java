package com.ELK_POC.elasticSearch.exception;

public class IllegalRequestParamException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IllegalRequestParamException(String s) {
        super(s);
    }
}