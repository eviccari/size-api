package br.com.company.products.size_api.stub;

import br.com.company.products.size_api.TestBase;
import br.com.company.products.size_api.adapters.SQLAdapter;
import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.exceptions.BadRequestException;
import br.com.company.products.size_api.exceptions.InternalServerErrorException;
import br.com.company.products.size_api.exceptions.NotFoundException;
import br.com.company.products.size_api.exceptions.UnprocessableEntityException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestSuccessScenarios extends TestBase{


    public SQLAdapter<SizeDTO> buildSQLAdapterStubInstance() {
    
        return new SQLAdapter<SizeDTO>() {

            List<SizeDTO> dtos = new ArrayList<>();

            @Override
            public void create(String sql, SizeDTO entity) throws InternalServerErrorException {
                dtos.add(entity);
            }

            @Override
            public SizeDTO findById(String sql, String id) throws InternalServerErrorException {
                var optional = dtos.stream().filter(d -> d.getId().equals(id)).findFirst();
                if(optional.isPresent()) {
                    return optional.get();
                } else {
                    return null;
                }
            }

            @Override
            public int update(String sql, SizeDTO entity) throws InternalServerErrorException {
                for (SizeDTO sizeDTO : dtos) {
                    if(sizeDTO.getId().equals(entity.getId())) {
                        sizeDTO.setDescription(entity.getDescription());
                        sizeDTO.setShortDescription(entity.getShortDescription());
                        sizeDTO.setSizeRange(entity.getSizeRange());
                        return 1;
                    }
                }
            
                return 0;
            }

            @Override
            public int delete(String sql, String id) throws InternalServerErrorException {
                for (int i = 0; i < dtos.size(); i++) {
                    if(dtos.get(i).getId().equals(id)){
                        dtos.remove(i);
                        return 1;
                    }
                }

                return 0;
            }
        };
    }

    @Test
    void ShouldCreateNewSize() throws InternalServerErrorException, UnprocessableEntityException {
        var dto = this.buildMockDTO();
        var service = this.buildServiceInstance(this.buildSQLAdapterStubInstance());

        var generatedId = service.create(dto);
        assertNotEquals(null, generatedId);
    }

    @Test
    void ShouldFindById() throws InternalServerErrorException, UnprocessableEntityException, BadRequestException, NotFoundException {
        var dto = this.buildMockDTO();
        var service = this.buildServiceInstance(this.buildSQLAdapterStubInstance());

        var generatedId = service.create(dto);
        assertNotEquals(null, generatedId);

        var dto2 = service.findById(generatedId);
        assertEquals(dto2.getDescription(), dto.getDescription());
    }

    @Test
    void ShouldUpdate() throws InternalServerErrorException, UnprocessableEntityException, BadRequestException, NotFoundException {
        var dto = this.buildMockDTO();
        var service = this.buildServiceInstance(this.buildSQLAdapterStubInstance());

        var generatedId = service.create(dto);
        assertNotEquals(null, generatedId);

        dto.setDescription(U_ANY_DESC);
        dto.setShortDescription(U_ANY_SDESC);
        dto.setSizeRange(U_ANY_SRANGE);
        dto.setId(generatedId);

        assertEquals(1, service.update(dto));

        var dto3 = service.findById(generatedId);
        assertEquals(U_ANY_DESC, dto3.getDescription());
        assertEquals(U_ANY_SDESC, dto3.getShortDescription());
        assertEquals(U_ANY_SRANGE, dto3.getSizeRange());
      }

      @Test
      void ShouldDeleteById() throws InternalServerErrorException, UnprocessableEntityException, BadRequestException, NotFoundException {
          var dto = this.buildMockDTO();
          var service = this.buildServiceInstance(this.buildSQLAdapterStubInstance());
  
          var generatedId = service.create(dto);
          assertNotEquals(null, generatedId);
  
          assertEquals(1, service.delete(generatedId));
          assertThrows(NotFoundException.class, () -> service.findById(generatedId));
      }
}
