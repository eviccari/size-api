package br.com.company.products.size_api.utils;

public class StringUtils {

    private StringUtils(){}

    public static final boolean isEmpty(String value) {
      return (value == null || value.isEmpty() || value.isBlank());
    }
    
}
