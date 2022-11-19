package br.com.company.products.size_api.services;

import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.exceptions.BadRequestException;
import br.com.company.products.size_api.exceptions.InternalServerErrorException;
import br.com.company.products.size_api.exceptions.NotFoundException;
import br.com.company.products.size_api.exceptions.UnprocessableEntityException;
import br.com.company.products.size_api.models.Size;
import br.com.company.products.size_api.repositories.Repository;
import br.com.company.products.size_api.utils.ObjectUtils;
import br.com.company.products.size_api.utils.StringUtils;

public class SizeService implements Service<SizeDTO>{

    public static final String NOT_FOUND_MESSAGE = "not found";

    private Repository<SizeDTO> repository;

    public SizeService(Repository<SizeDTO> repository) {
        this.repository = repository;
    }

    @Override
    public String create(SizeDTO entity) throws InternalServerErrorException, UnprocessableEntityException {
        var model = ObjectUtils.convertFrom(entity);
        model.generateId(); 
        model.validate();

        entity.setId(model.getId());
        this.repository.create(entity);
        return model.getId();
    }

    @Override
    public SizeDTO findById(String id) throws InternalServerErrorException, BadRequestException, NotFoundException {
        if(StringUtils.isEmpty(id)) 
            throw new BadRequestException(Size.ID_IS_REQUIRED_MESSAGE);

        var result = this.repository.findById(id);  
        if(result != null){
            return result;
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public int update(SizeDTO entity) throws InternalServerErrorException, NotFoundException, UnprocessableEntityException {
        var model = ObjectUtils.convertFrom(entity);
        model.validate();

        entity.setId(model.getId());
        var rowCount = this.repository.update(entity);
        
        if(rowCount > 0) {
            return rowCount;
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public int delete(String id) throws InternalServerErrorException, BadRequestException {
        if(StringUtils.isEmpty(id)) 
            throw new BadRequestException(Size.ID_IS_REQUIRED_MESSAGE);

        return this.repository.delete(id);  
    }
    
}
