package br.com.company.products.size_api.services;

import br.com.company.products.size_api.exceptions.BadRequestException;
import br.com.company.products.size_api.exceptions.InternalServerErrorException;
import br.com.company.products.size_api.exceptions.NotFoundException;
import br.com.company.products.size_api.exceptions.UnprocessableEntityException;

public interface Service<T> {

    String create(T entity) throws InternalServerErrorException, UnprocessableEntityException;
    T findById(String id) throws InternalServerErrorException, BadRequestException, NotFoundException;
    int update(T entity) throws InternalServerErrorException, NotFoundException, UnprocessableEntityException;
    int delete(String id) throws InternalServerErrorException, BadRequestException;
}
