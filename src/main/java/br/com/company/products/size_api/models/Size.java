package br.com.company.products.size_api.models;

import java.util.UUID;

import br.com.company.products.size_api.exceptions.UnprocessableEntityException;
import br.com.company.products.size_api.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
public class Size implements Validatable, Identifiable{

    public static final String ID_IS_REQUIRED_MESSAGE = "id is required";

    @Getter
    @Setter
    private String id;

    private String description;

    private String shortDescription;

    private String sizeRange;

    @Override
    public void validate() throws UnprocessableEntityException {
        if(StringUtils.isEmpty(this.id))
            throw new UnprocessableEntityException(ID_IS_REQUIRED_MESSAGE);        
        
        if(StringUtils.isEmpty(this.description))
            throw new UnprocessableEntityException("description is required");        

        if(StringUtils.isEmpty(this.shortDescription))
            throw new UnprocessableEntityException("shortDescription is required");        

        if(StringUtils.isEmpty(this.sizeRange))
            throw new UnprocessableEntityException("sizeRange is required");        
    }

    @Override
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}
