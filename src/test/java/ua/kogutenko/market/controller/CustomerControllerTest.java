package ua.kogutenko.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.kogutenko.market.dto.CustomerDTO;
import ua.kogutenko.market.exception.CustomerNotFoundException;
import ua.kogutenko.market.repository.CustomerRepository;
import ua.kogutenko.market.service.impl.CustomerServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerControllerTest.class);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CustomerServiceImpl service;
    @MockBean
    private CustomerRepository repository;

    CustomerDTO RECORD_1 = new CustomerDTO(1L,
                                           1L,
                                           1L,
                                           true,
                                           "John",
                                           "john@mail.ua",
                                           "+111111"

    );
    CustomerDTO RECORD_2 = new CustomerDTO(2L,
                                           1L,
                                           1L,
                                           true,
                                           "Bob",
                                           "Bob@mail.ua",
                                           "+22222222"
    );
    CustomerDTO RECORD_3 = new CustomerDTO(3L,
                                           1L,
                                           1L,
                                           true,
                                           "Carl",
                                           "carl@mail.ua",
                                           "+3333333"
    );

    @BeforeEach
    void setUp() {
        service.save(RECORD_1);
        service.save(RECORD_2);
        service.save(RECORD_3);
    }

    @Test
    @DisplayName("GET /api/customers success")
    public void getAllCustomers_success() throws Exception {
        List<CustomerDTO> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(service.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/customers")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].full_name", is("Carl")));
    }

    @Test
    @DisplayName("GET /api/customers/1 success")
    public void getCustomerById_success() throws Exception {
        Mockito.when(service.findById(RECORD_1.getId())).thenReturn(Optional.ofNullable(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/customers/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.full_name", is("John")));
    }

    @Test
    @DisplayName("GET /api/customers/4 - Not Found")
    void getCustomerById_notFound() throws Exception {
        // Setup our mocked service
        doReturn(Optional.empty()).when(service).findById(anyLong());

        // Execute the GET request
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/customers/{id}", anyLong()))
                // Validate the response code
                .andExpect(status().isNotFound());
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("POST /api/customers success")
    public void createCustomer_success() throws Exception {
        System.out.println(service.findAll());
        CustomerDTO record = CustomerDTO.builder()
                .id(4L)
                .full_name("Anna")
                .email("carl@mail.ua")
                .build();

        Mockito.when(service.save(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.full_name", is("Anna")))
                .andExpect(jsonPath("$.email", is("carl@mail.ua")));
    }


//    @Test
//    @DisplayName("PUT /api/customers/1 success")
//    void updateCustomer_success() throws Exception {
//        CustomerDTO updated = CustomerDTO.builder()
//                .id(1L)
//                .phone("+4574673")
//                .build();
//        CustomerDTO existing = RECORD_1;
//
////        Mockito.when(service.findById(existing.getId())).thenReturn(Optional.of(existing));
//        LOG.info(String.valueOf(service.findAll()));
//        Mockito.when(service.save(existing)).thenReturn(existing);
//        existing.setPhone("+4574673");
//        Mockito.when(service.update(updated, 1L)).thenReturn(existing);
//
//        mockMvc.perform(
//                put("/api/customers/{id}", 1)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .content(asJsonString(updated)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(notNullValue()))
//                .andExpect(jsonPath("$.full_name").value("John"))
//                .andExpect(jsonPath("$.email").value("john@mail.ua"))
//                .andExpect(jsonPath("$.phone").value("+4574673"));
//    }


    @Test
    @DisplayName("PUT /api/customers/1 Not Found ID")
    public void updatePatientRecord_nullId() throws Exception {
        // Setup our mocked service
        CustomerDTO widgetToPut = CustomerDTO.builder().id(1L).phone("+124567").build();
        CustomerDTO widgetToReturnFindBy = CustomerDTO.builder()
                .id(1L).full_name("A").email("a@mail.com").is_active(true)
                .phone(null).build();
        CustomerDTO widgetToReturnSave = CustomerDTO.builder()
                .id(1L).full_name("A").email("a@mail.com").is_active(true)
                .phone("+124567").build();

        doReturn(Optional.ofNullable(widgetToReturnFindBy)).when(service).findById(1L);
        doReturn(widgetToReturnSave).when(service).update(widgetToReturnFindBy, 1L);

        // Execute the POST request
        mockMvc.perform(
                        put("/api/customers/{id}", 2)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(widgetToPut)))

                .andExpect(status().isNotFound())
                .andExpect(result ->
                                   assertTrue(
                                           (result.getResolvedException()
                                                   instanceof CustomerNotFoundException)))
                .andExpect(result ->
                                   assertEquals(
                                           "Customer not found with this id = 2",
                                           result.getResolvedException().getMessage()
                                   ));


    }

    @Test
    @DisplayName("PUT /api/customers/1 Null Param")
    public void updateCustomer_nullParam() throws Exception {
        // Setup our mocked service
        CustomerDTO widgetToReturnFindBy = CustomerDTO.builder().id(1L).full_name("A").email("a@mail.com").is_active(
                true).build();
        CustomerDTO widgetToReturnSave = CustomerDTO.builder().id(1L).full_name("A").email("a@mail.com").is_active(
                true).phone("+124567").build();

        doReturn(Optional.of(widgetToReturnFindBy)).when(service).findById(1L);
        doReturn(widgetToReturnSave).when(service).update(widgetToReturnFindBy, 1L);

        // Execute the POST request
        mockMvc.perform(
                        put("/api/customers/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(null)))

                .andExpect(status().isBadRequest())
                .andExpect(result ->
                                   assertTrue(
                                           (result.getResolvedException()
                                                   instanceof HttpMessageNotReadableException)));


    }

    @Test
    @DisplayName("DELETE /api/customers/1 success")
    public void deleteCustomerById_success() throws Exception {
        Mockito.when(service.findById(RECORD_2.getId())).thenReturn(Optional.ofNullable(RECORD_2));

        mockMvc.perform(MockMvcRequestBuilders
                                .delete("/api/customers/2")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/customers/50 not found id")
    public void deletePatientById_notFound() throws Exception {
        doNothing().when(service).delete(50L);
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/customers/{id}", 50L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                                   assertTrue(
                                           result.getResolvedException() instanceof CustomerNotFoundException
                                   )
                );
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}