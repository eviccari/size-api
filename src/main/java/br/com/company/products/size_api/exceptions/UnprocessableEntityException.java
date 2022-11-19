package br.com.company.products.size_api.exceptions;

public class UnprocessableEntityException extends Exception implements HTTPException<Integer>{

    public static final Integer STATUS_CODE = 422;

    public UnprocessableEntityException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return STATUS_CODE;
    }
    
}
