package br.com.company.products.size_api.integrated;

import static org.junit.Assert.assertThrows;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.company.products.size_api.TestBase;
import br.com.company.products.size_api.adapters.SQLAdapter;
import br.com.company.products.size_api.adapters.SizeSQLAdapter;
import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.exceptions.InternalServerErrorException;
import br.com.company.products.size_api.repositories.Repository;

@Sql({"/create-table.sql"})
@RunWith(SpringRunner.class)
class TestDatabaseErrors extends TestBase{

    @Autowired
    DataSource dataSource;

    public Repository<SizeDTO> buildRepositoryStubInstance(SQLAdapter<SizeDTO> sqlAdapter) {
        
        return new Repository<SizeDTO>() {

            @Override
            public void create(SizeDTO entity) throws InternalServerErrorException {
                var sql = """
                    inser into products.sizes (id, description, short_description, size_range)
                    values (:id, :description, :shortDescription, :sizeRange)
                  """;
                sqlAdapter.create(sql, entity);
            }

            @Override
            public SizeDTO findById(String id) throws InternalServerErrorException {
                var sql = """
                    selec * from products.sizes where id = :id
                 """;
               return sqlAdapter.findById(sql, id);
            }

            @Override
            public int update(SizeDTO entity) throws InternalServerErrorException {
                var sql = """
                    update products.sizes 
                        se description = :description,
                            short_description = :shortDescription,
                            size_range = :sizeRange
                      where id = :id        
                  """;
                return sqlAdapter.update(sql, entity);
            }

            @Override
            public int delete(String id) throws InternalServerErrorException {
                var sql = """
                    delete fro products.sizes where id = :id
                 """;
                return sqlAdapter.delete(sql, id);
           }
            
        };
    }
    

    @Test
    void ShouldRiseInternalServerErrorException() {
        var sqlAdapter = new SizeSQLAdapter(this.dataSource);
        var service = this.buildServiceInstance(this.buildRepositoryStubInstance(sqlAdapter));
        var dto = this.buildMockDTO();

        var id = this.generateMockId();
        dto.setId(id);

        assertThrows(InternalServerErrorException.class, () -> service.create(dto));
        assertThrows(InternalServerErrorException.class, () -> service.findById(id));
        assertThrows(InternalServerErrorException.class, () -> service.update(dto));
        assertThrows(InternalServerErrorException.class, () -> service.delete(id));
    }

}
