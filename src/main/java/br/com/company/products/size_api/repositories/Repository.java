package br.com.company.products.size_api.repositories;

import br.com.company.products.size_api.exceptions.InternalServerErrorException;

public interface Repository<T> {

    void create(T entity) throws InternalServerErrorException;
    T findById(String id) throws InternalServerErrorException;
    int update(T entity) throws InternalServerErrorException;
    int delete(String id) throws InternalServerErrorException;

}
