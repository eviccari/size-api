package br.com.company.products.size_api.controllers.v1;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.company.products.size_api.adapters.SizeSQLAdapter;
import br.com.company.products.size_api.controllers.BaseController;
import br.com.company.products.size_api.dtos.CreateOKDTO;
import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.dtos.UpdateRowCountDTO;
import br.com.company.products.size_api.exceptions.BadRequestException;
import br.com.company.products.size_api.exceptions.InternalServerErrorException;
import br.com.company.products.size_api.exceptions.NotFoundException;
import br.com.company.products.size_api.exceptions.UnprocessableEntityException;
import br.com.company.products.size_api.repositories.SizeRepository;
import br.com.company.products.size_api.services.Service;
import br.com.company.products.size_api.services.SizeService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/sizes")
@Slf4j
public class SizeController extends BaseController{

    @Autowired
    DataSource dataSource;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateOKDTO> create(@RequestBody SizeDTO dto) throws InternalServerErrorException, UnprocessableEntityException, NotFoundException{

        log.info(String.format("creating with payload %s", dto.toString()));
        var service = this.buildServiceInstance();
        var generatedId = service.create(dto);
        log.info(String.format("generatedId %s", generatedId));

        return new ResponseEntity<>(
            CreateOKDTO.builder()
                .generatedId(generatedId)
            .build(), 
            HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SizeDTO> findById(@PathVariable String id) throws InternalServerErrorException, BadRequestException, NotFoundException{

        log.info(String.format("finding with id %s", id));
        var service = this.buildServiceInstance();
        var result = service.findById(id);
        log.info(String.format("size with payload %s", result.toString()));

        return new ResponseEntity<>(
            result, 
            HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateRowCountDTO> update(@RequestBody SizeDTO dto, @PathVariable String id) throws InternalServerErrorException, BadRequestException, NotFoundException, UnprocessableEntityException{

        log.info(String.format("updating with payload %s with id %s", dto.toString(), id));
        var service = this.buildServiceInstance();
        dto.setId(id);
        var result = service.update(dto);
        log.info(String.format("update row count %d with id %s", result, id));

        return new ResponseEntity<>(
            UpdateRowCountDTO.builder().id(id).updatedRowCount(result).build(), 
            HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateRowCountDTO> delete(@PathVariable String id) throws InternalServerErrorException, BadRequestException {

        log.info(String.format("deleting with id %s", id));
        var service = this.buildServiceInstance();
        var result = service.delete(id);
        log.info(String.format("update row count %d", result));

        return new ResponseEntity<>(
            UpdateRowCountDTO.builder().updatedRowCount(result).id(id).build(), 
            HttpStatus.OK
        );
    }

    private Service<SizeDTO> buildServiceInstance() {
       var sqlAdapter = new SizeSQLAdapter(this.dataSource);
       var repository = new SizeRepository(sqlAdapter);
       return new SizeService(repository); 
    }

}
