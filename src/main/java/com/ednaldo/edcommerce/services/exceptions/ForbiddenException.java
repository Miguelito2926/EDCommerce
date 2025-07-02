package com.ednaldo.edcommerce.services.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String mgs) {
        super(mgs);
    }
}
