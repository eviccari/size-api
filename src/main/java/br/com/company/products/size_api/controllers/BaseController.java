package br.com.company.products.size_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.company.products.size_api.dtos.ResponseWithErrorDTO;
import br.com.company.products.size_api.exceptions.BadRequestException;
import br.com.company.products.size_api.exceptions.HTTPException;
import br.com.company.products.size_api.exceptions.InternalServerErrorException;
import br.com.company.products.size_api.exceptions.NotFoundException;
import br.com.company.products.size_api.exceptions.UnprocessableEntityException;

public class BaseController {

    @ExceptionHandler({
        BadRequestException.class, 
        InternalServerErrorException.class,
        NotFoundException.class,
        UnprocessableEntityException.class
    })
    public ResponseEntity<ResponseWithErrorDTO> handleErrors(HTTPException<Integer> ex) {
        return new ResponseEntity<>(
            ResponseWithErrorDTO.builder()
                .errorMessage(ex.getMessage())
                .statusCode(ex.getStatusCode())
                .build(),
            HttpStatus.valueOf(ex.getStatusCode())
        );
    }    
    
}
