package br.com.company.products.size_api.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
public class ResponseWithErrorDTO implements Serializable{

    @Getter
    private String errorMessage;

    @Getter
    private Integer statusCode;
    
}
