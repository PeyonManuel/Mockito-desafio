package com.coderhouse.controller;

import com.coderhouse.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void antesDeTodos(){
        System.out.print("Mensaje antes que todos los tests");
    }

    @BeforeEach
    void antesDeCadaUno() {
        System.out.print("Mensaje antes de cada uno de los tests");
    }

    @Test
    public void testGetAll() throws Exception {
            MvcResult result = mockMvc.perform(get("/coder-house/mensajes/all"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
            String content = result.getResponse().getContentAsString();
            List<Message> messageValues = mapper.readValue(content, List.class);
            Assert.notNull(messageValues, "La lista de mensajes es nula");
            Assert.notEmpty(messageValues, "La lista de mensajes esta vacia");
            Assert.isTrue(messageValues.size() == 5, "La lista no tiene la longitud que tendria que tener");
    }

    @Test
    public void testGetByDescription() throws Exception {
        MvcResult result = mockMvc.perform(get("/coder-house/mensajes?description=Mensaje-ABCD"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Mensaje-ABCD")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        List<Message> messageValues = mapper.readValue(content, List.class);
        Assert.notNull(messageValues, "La lista de mensajes es nula");
        Assert.notEmpty(messageValues, "La lista de mensajes esta vacia");
        Assert.isTrue(messageValues.size() == 3, "La lista no tiene la longitud que tendria que tener");
    }

    @Test
    public void testGetById() throws Exception {
        MvcResult result = mockMvc.perform(get("/coder-house/mensajes/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Message messageValues = mapper.readValue(content, Message.class);
        Assert.notNull(messageValues, "La lista de mensajes es nula");
        Assert.isTrue(messageValues.getDescription().equals("Mensaje-ABCE"), "El mensaje no tiene la descripcion que tendria que tener");
    }
}
