package org.example.sportsorder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sportsorder.BaseIntegrationTest;
import org.example.sportsorder.controllers.mutilation.dto.MutilationRequest;
import org.example.sportsorder.models.Mutilation;
import org.example.sportsorder.repositories.MutilationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.example.sportsorder.utils.Models.MUTILATION;
import static org.example.sportsorder.utils.Models.MUTILATIONREQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MutilationControllerTest extends BaseIntegrationTest {
    @Autowired
    private MutilationRepository mutilationRepository;

    private Mutilation mutilation;

    @BeforeEach
    void setUp() {
        mutilation = mutilationRepository.save(MUTILATION);
    }

    @AfterEach
    void down(){
        mutilationRepository.deleteAll();
    }

    @Test
    void createMutilationShouldReturnCreated() throws Exception {
        MutilationRequest mutilationRequest = MUTILATIONREQUEST;

        mockMvc.perform(post("/mutilations")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mutilationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void findMutilationByIdShouldReturnResult() throws Exception {
        mockMvc.perform(get("/mutilations/" + mutilation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(mutilation.getType()));
    }

    @Test
    void deleteMutilationShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/mutilations/" + mutilation.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findAllMutilationsShouldReturnPagedResults() throws Exception {

        mockMvc.perform(get("/mutilations?page=0&size=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].type").value(mutilation.getType()));
    }

    @Test
    void updateMutilationShouldReturnUpdated() throws Exception {
        MutilationRequest mutilationRequest = MUTILATIONREQUEST;

        mockMvc.perform(put("/mutilations/" + mutilation.getId())
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mutilationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(mutilationRequest.type()));
    }
}