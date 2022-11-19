package br.com.company.products.size_api.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOKDTO implements Serializable{
    
    @Getter
    @Setter
    private String generatedId;
}
