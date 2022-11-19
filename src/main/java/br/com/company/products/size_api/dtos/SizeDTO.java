package br.com.company.products.size_api.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SizeDTO implements Serializable{

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String shortDescription;

    @Getter
    @Setter
    private String sizeRange;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
    
}
