package br.com.company.products.size_api.integrated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import br.com.company.products.size_api.TestBase;
import br.com.company.products.size_api.dtos.CreateOKDTO;
import br.com.company.products.size_api.dtos.SizeDTO;
import br.com.company.products.size_api.dtos.UpdateRowCountDTO;

@Sql({"/create-table.sql"})
@RunWith(SpringRunner.class)
class TestController extends TestBase{

    public static final String BASE_PATH = "/api/v1/sizes";

    @Autowired
    MockMvc mockMvc;

    @Test
    void ShouldCreateNewSize() throws Exception {
        var dto = this.buildMockDTO();

        var request = MockMvcRequestBuilders
            .post(BASE_PATH + "/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(dto.toString());

        var response = mockMvc.perform(request).andReturn();
        assertEquals(201, response.getResponse().getStatus());

        var createOK = new Gson().fromJson(
            response
            .getResponse()
            .getContentAsString(), 
        CreateOKDTO.class);

        assertNotEquals(null, createOK.getGeneratedId());
    }

    @Test
    void ShouldFindSizeById() throws Exception {
        var dto = this.buildMockDTO();

        var request = MockMvcRequestBuilders
            .post(BASE_PATH + "/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(dto.toString());

        var response = mockMvc.perform(request).andReturn();
        assertEquals(201, response.getResponse().getStatus());

        var createOK = new Gson().fromJson(
            response
            .getResponse()
            .getContentAsString(), 
        CreateOKDTO.class);
        dto.setId(createOK.getGeneratedId());

        request = MockMvcRequestBuilders.get(BASE_PATH + "/" + createOK.getGeneratedId());
        response = mockMvc.perform(request).andReturn();

        assertEquals(200, response.getResponse().getStatus());

        var dto2 = new Gson().fromJson(response.getResponse().getContentAsString(), SizeDTO.class); 
        assertEquals(dto2.getDescription(), dto.getDescription());
        assertEquals(dto2.getShortDescription(), dto.getShortDescription());
        assertEquals(dto2.getSizeRange(), dto.getSizeRange());
        assertEquals(dto2.getId(), dto.getId());
    }

    @Test
    void ShouldUpdateSize() throws Exception {
        var dto = this.buildMockDTO();

        var request = MockMvcRequestBuilders
            .post(BASE_PATH + "/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(dto.toString());

        var response = mockMvc.perform(request).andReturn();
        assertEquals(201, response.getResponse().getStatus());

        var createOK = new Gson().fromJson(
            response
            .getResponse()
            .getContentAsString(), 
        CreateOKDTO.class);
        dto.setId(createOK.getGeneratedId());

        dto.setDescription(U_ANY_DESC);
        dto.setShortDescription(U_ANY_SDESC);
        dto.setSizeRange(U_ANY_SRANGE);

        request = MockMvcRequestBuilders
            .put(BASE_PATH + "/" + createOK.getGeneratedId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(dto.toString());

        response = mockMvc.perform(request).andReturn();
        assertEquals(200, response.getResponse().getStatus());

        var updateRowCount = new Gson()
            .fromJson(
                response
                .getResponse()
                .getContentAsString(),
        UpdateRowCountDTO.class);
        assertEquals(1, updateRowCount.getUpdatedRowCount());

        request = MockMvcRequestBuilders
            .get(BASE_PATH + "/" + createOK.getGeneratedId());

        response = mockMvc.perform(request).andReturn();    

        var dto2 = new Gson().fromJson(response.getResponse().getContentAsString(), SizeDTO.class); 
        assertEquals(U_ANY_DESC, dto2.getDescription());
        assertEquals(U_ANY_SDESC, dto2.getShortDescription());
        assertEquals(U_ANY_SRANGE, dto2.getSizeRange());
        assertEquals(createOK.getGeneratedId(), dto2.getId());
    }

    @Test
    void ShouldDeleteSize() throws Exception {
        var dto = this.buildMockDTO();

        var request = MockMvcRequestBuilders
            .post(BASE_PATH + "/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(dto.toString());

        var response = mockMvc.perform(request).andReturn();
        assertEquals(201, response.getResponse().getStatus());

        var createOK = new Gson().fromJson(
            response
            .getResponse()
            .getContentAsString(), 
        CreateOKDTO.class);

        request = MockMvcRequestBuilders
            .delete(BASE_PATH + "/" + createOK.getGeneratedId());

        response = mockMvc.perform(request).andReturn();
        assertEquals(200, response.getResponse().getStatus());    

        var updateRowCount = new Gson()
            .fromJson(
                response
                .getResponse()
                .getContentAsString(),
        UpdateRowCountDTO.class);
        assertEquals(1, updateRowCount.getUpdatedRowCount());
    }

    @Test
    void ShouldGetUnprocessableEntity() throws Exception {
        var dto = this.buildMockDTO();
        dto.setDescription(NULL_STRING);

        var request = MockMvcRequestBuilders
            .post(BASE_PATH + "/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(dto.toString());

        var response = mockMvc.perform(request).andReturn();
        assertEquals(422, response.getResponse().getStatus());        
    }

    @Test
    void ShouldGetNotFoundException() throws Exception {
        var dto = this.buildMockDTO();

        var request = MockMvcRequestBuilders
            .post(BASE_PATH + "/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(dto.toString());

        var response = mockMvc.perform(request).andReturn();
        assertEquals(201, response.getResponse().getStatus());        

        var createOK = new Gson().fromJson(
            response
            .getResponse()
            .getContentAsString(), 
        CreateOKDTO.class);

        request = MockMvcRequestBuilders
            .get(BASE_PATH + "/" + createOK.getGeneratedId() + "ABC");

        response = mockMvc.perform(request).andReturn();
        assertEquals(404, response.getResponse().getStatus());

        request = MockMvcRequestBuilders
            .put(BASE_PATH + "/" + createOK.getGeneratedId() + "ABC")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(dto.toString());

        response = mockMvc.perform(request).andReturn();
        assertEquals(404, response.getResponse().getStatus());
    }
}
