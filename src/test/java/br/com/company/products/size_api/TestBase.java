package br.com.company.products.size_api;

import java.util.UUID;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.company.products.size_api.adapters.SQLAdapter;
import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.repositories.Repository;
import br.com.company.products.size_api.repositories.SizeRepository;
import br.com.company.products.size_api.services.Service;
import br.com.company.products.size_api.services.SizeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestBase {

    public static final String ANY_DESC = "any desc";
    public static final String ANY_SDESC = "sdesc";
    public static final String ANY_SRANGE = "any_s_range";
    public static final String ANY_ID = "any_id";

    public static final String U_ANY_DESC = "u desc";
    public static final String U_ANY_SDESC = "udesc";
    public static final String U_ANY_SRANGE = "u_s_range";

    public static final String NULL_STRING = null;

    public SizeDTO buildMockDTO() {
        return SizeDTO.builder()
            .description(ANY_DESC)
            .shortDescription(ANY_SDESC)
            .sizeRange(ANY_SRANGE)
            .id(ANY_ID)
        .build();
    }

    public String generateMockId() {
        return UUID.randomUUID().toString();
    }

    public Service<SizeDTO> buildServiceInstance(SQLAdapter<SizeDTO> sqlAdapter) {
        var repository = new SizeRepository(sqlAdapter);
        return new SizeService(repository);
    }

    public Service<SizeDTO> buildServiceInstance(Repository<SizeDTO> repository) {
        return new SizeService(repository);
    }

}
