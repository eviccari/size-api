package br.com.company.products.size_api.stub;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.company.products.size_api.TestBase;
import br.com.company.products.size_api.adapters.SQLAdapter;
import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.exceptions.BadRequestException;
import br.com.company.products.size_api.exceptions.InternalServerErrorException;
import br.com.company.products.size_api.exceptions.NotFoundException;
import br.com.company.products.size_api.exceptions.UnprocessableEntityException;
import br.com.company.products.size_api.utils.ObjectUtils;

class TestExceptions extends TestBase{

    public SQLAdapter<SizeDTO> buildSQLAdapterStubInstance() {
        return new SQLAdapter<SizeDTO>() {

            @Override
            public void create(String sql, SizeDTO entity) throws InternalServerErrorException {
                throw new InternalServerErrorException(new Throwable("error"));
                
            }

            @Override
            public SizeDTO findById(String sql, String id) throws InternalServerErrorException {
                return null;
            }

            @Override
            public int update(String sql, SizeDTO entity) throws InternalServerErrorException {
                return 0;
            }

            @Override
            public int delete(String sql, String id) throws InternalServerErrorException {
                return 0;
            }
            
        };
    }


    @Test
    void ShouldRiseUnprocessableEntityException() {
        var dto = this.buildMockDTO();
        dto.setDescription(NULL_STRING);

        var service = this.buildServiceInstance(this.buildSQLAdapterStubInstance());
        assertThrows(UnprocessableEntityException.class, () -> service.create(dto));

        var dto2 = this.buildMockDTO();
        dto2.setShortDescription(NULL_STRING);

        assertThrows(UnprocessableEntityException.class, () -> service.create(dto2));

        var dto3 = this.buildMockDTO();
        dto3.setSizeRange(NULL_STRING);

        assertThrows(UnprocessableEntityException.class, () -> service.create(dto3));

        var dto4 = this.buildMockDTO();
        var model = ObjectUtils.convertFrom(dto4);        
        model.setId(NULL_STRING);

        assertThrows(UnprocessableEntityException.class, () -> model.validate());
    }

    @Test
    void ShouldRiseBadRequestException() {
        var id = NULL_STRING;
        var service = this.buildServiceInstance(this.buildSQLAdapterStubInstance());

        assertThrows(BadRequestException.class, () -> service.findById(id));
        assertThrows(BadRequestException.class, () -> service.delete(id));
    }

    @Test
    void ShouldRiseInternalServerErrorException() {
        var dto = this.buildMockDTO();
        var service = this.buildServiceInstance(this.buildSQLAdapterStubInstance());

        assertThrows(InternalServerErrorException.class, () -> service.create(dto));
    }


    @Test
    void ShouldRiseNotFoundException() {
        var id = this.generateMockId();
        var service = this.buildServiceInstance(this.buildSQLAdapterStubInstance());
        var dto = this.buildMockDTO();

        assertThrows(NotFoundException.class, () -> service.findById(id));
        assertThrows(NotFoundException.class, () -> service.update(dto));
    }

}
