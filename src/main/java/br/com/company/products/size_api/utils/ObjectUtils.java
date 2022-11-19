package br.com.company.products.size_api.utils;

import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.models.Size;

public class ObjectUtils {


    private ObjectUtils() {}

    public static final Size convertFrom(SizeDTO dto) {
        return Size.builder()
            .description(dto.getDescription())
            .shortDescription(dto.getShortDescription())
            .sizeRange(dto.getSizeRange())
            .id(dto.getId())
        .build();
    }

    
}
