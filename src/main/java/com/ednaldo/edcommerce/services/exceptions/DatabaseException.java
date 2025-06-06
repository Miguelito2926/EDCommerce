package com.ednaldo.edcommerce.services.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String mgs) {
        super(mgs);
    }
}
