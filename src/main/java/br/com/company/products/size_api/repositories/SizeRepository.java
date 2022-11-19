package br.com.company.products.size_api.repositories;

import br.com.company.products.size_api.adapters.SQLAdapter;
import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.exceptions.InternalServerErrorException;

public class SizeRepository implements Repository<SizeDTO>{

    private SQLAdapter<SizeDTO> sqlAdapter;

    public SizeRepository(SQLAdapter<SizeDTO> sqlAdapter) {
        this.sqlAdapter = sqlAdapter;
    }

    @Override
    public void create(SizeDTO entity) throws InternalServerErrorException {
        var sql = """
                    insert into products.sizes (id, description, short_description, size_range)
                    values (:id, :description, :shortDescription, :sizeRange)
                  """;
        this.sqlAdapter.create(sql, entity);
    }

    @Override
    public SizeDTO findById(String id) throws InternalServerErrorException {
        var sql = """
                     select * from products.sizes where id = :id
                  """;
        return this.sqlAdapter.findById(sql, id);
    }

    @Override
    public int update(SizeDTO entity) throws InternalServerErrorException {
        var sql = """
                    update products.sizes 
                        set description = :description,
                            short_description = :shortDescription,
                            size_range = :sizeRange
                      where id = :id        
                  """;
        return this.sqlAdapter.update(sql, entity);

    }

    @Override
    public int delete(String id) throws InternalServerErrorException {
        var sql = """
                     delete from products.sizes where id = :id
                  """;
        return this.sqlAdapter.delete(sql, id);
    }
    
}
