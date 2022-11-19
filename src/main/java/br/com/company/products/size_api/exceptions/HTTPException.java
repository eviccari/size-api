package br.com.company.products.size_api.exceptions;

public interface HTTPException<T> {
    
    T getStatusCode();
    String getMessage();

}
