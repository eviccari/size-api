package br.com.company.products.size_api.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRowCountDTO implements Serializable{
    
    @Getter
    private String id;
    
    @Getter
    private Integer updatedRowCount;

}
