package br.com.company.products.size_api.exceptions;

public class NotFoundException extends Exception implements HTTPException<Integer>{

    public static final Integer STATUS_CODE = 404;

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return STATUS_CODE;
    }
    
}
