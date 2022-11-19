package br.com.company.products.size_api.adapters;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.exceptions.InternalServerErrorException;

public class SizeSQLAdapter implements SQLAdapter<SizeDTO>{

    private DataSource dataSource;

    public SizeSQLAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(String sql, SizeDTO entity) throws InternalServerErrorException {
        try {
            new NamedParameterJdbcTemplate(this.dataSource)
                .update(
                    sql, 
                    new BeanPropertySqlParameterSource(entity)
            );            
        } catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae);
        }
        
    }

    @Override
    public SizeDTO findById(String sql, String id) throws InternalServerErrorException {
        try {
            return new NamedParameterJdbcTemplate(this.dataSource)
                .queryForObject(
                    sql, 
                new MapSqlParameterSource().addValue("id", id), 
                BeanPropertyRowMapper.newInstance(SizeDTO.class)
            );
        } catch (EmptyResultDataAccessException erde){
            return null;
        } catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae);
        }    
    }

    @Override
    public int update(String sql, SizeDTO entity) throws InternalServerErrorException {
        try {
            return new NamedParameterJdbcTemplate(this.dataSource)
                .update(
                    sql, 
                    new BeanPropertySqlParameterSource(entity)
            );            
        } catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae);
        }    
    }

    @Override
    public int delete(String sql, String id) throws InternalServerErrorException {
        try {
            return new NamedParameterJdbcTemplate(this.dataSource)
                .update(
                    sql, 
                    new MapSqlParameterSource().addValue("id", id)
                );
        } catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae);
        }      
    }
}
