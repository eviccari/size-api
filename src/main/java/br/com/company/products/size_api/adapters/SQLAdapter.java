package br.com.company.products.size_api.adapters;

import br.com.company.products.size_api.exceptions.InternalServerErrorException;

public interface SQLAdapter<T> {
    void create(String sql, T entity) throws InternalServerErrorException;
    T findById(String sql, String id) throws InternalServerErrorException;
    int update(String sql, T entity) throws InternalServerErrorException;
    int delete(String sql, String id) throws InternalServerErrorException;
}
