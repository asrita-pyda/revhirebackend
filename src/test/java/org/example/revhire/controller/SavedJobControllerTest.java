package org.example.revhire.controller;

import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.service.SavedJobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SavedJobController.class)
@AutoConfigureMockMvc(addFilters = false)
class SavedJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SavedJobService savedJobService;

    @MockBean
    private org.example.revhire.config.JwtUtils jwtUtils;

    @MockBean
    private org.example.revhire.repository.UserRepository userRepository;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void saveJob_Success() throws Exception {
        mockMvc.perform(post("/api/saved-jobs/1/10"))
                .andExpect(status().isOk());
    }

    @Test
    void unsaveJob_Success() throws Exception {
        mockMvc.perform(delete("/api/saved-jobs/1/10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getSavedJobs_Success() throws Exception {
        when(savedJobService.getSavedJobs(1L)).thenReturn(List.of(new JobResponse()));
        mockMvc.perform(get("/api/saved-jobs/1"))
                .andExpect(status().isOk());
    }
}

