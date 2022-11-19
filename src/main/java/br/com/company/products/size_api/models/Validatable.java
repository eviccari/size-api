package br.com.company.products.size_api.models;

import br.com.company.products.size_api.exceptions.UnprocessableEntityException;

public interface Validatable {
    void validate() throws UnprocessableEntityException;
}
