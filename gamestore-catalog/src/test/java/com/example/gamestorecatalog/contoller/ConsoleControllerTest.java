package com.example.gamestorecatalog.contoller;

import com.example.gamestorecatalog.model.Console;
import com.example.gamestorecatalog.repository.ConsoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ConsoleControllerTest {



    @Test
    public void getConsole() {
    }

    @Test
    public void updateConsole() {
    }

    @Test
    public void deleteConsole() {
    }

    @Test
    public void getConsoleByManufacturer() {
    }

    @Test
    public void getAllConsoles() {
    }
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ConsoleRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void createConsole() throws Exception {
        Console console = new Console();
        console.setManufacturer("Sony");
        console.setModel("Playstation 4");
        console.setMemoryAmount("1TB");
        console.setPrice(new BigDecimal(299.99));
        console.setQuantity(1);

        String inputJson = mapper.writeValueAsString(console);
        mockMvc.perform(MockServerHttpRequest.post("/console")
                .body(inputJson)
            //    .contentType(MediaType.APPLICATION_JSON))
            //    .andExpect(status().isCreated());



    }
}