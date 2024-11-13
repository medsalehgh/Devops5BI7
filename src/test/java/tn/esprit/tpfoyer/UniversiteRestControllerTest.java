package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.IUniversiteService;
import tn.esprit.tpfoyer.control.UniversiteRestController;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UniversiteRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUniversiteService universiteService;

    @InjectMocks
    private UniversiteRestController universiteRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(universiteRestController).build();
    }

    @Test
    void testGetUniversites() throws Exception {
        Universite uni1 = new Universite(1L, "Université de Test 1", "Adresse 1");
        Universite uni2 = new Universite(2L, "Université de Test 2", "Adresse 2");

        List<Universite> universites = Arrays.asList(uni1, uni2);

        when(universiteService.retrieveAllUniversites()).thenReturn(universites);

        mockMvc.perform(get("/universite/retrieve-all-universites")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nomUniversite").value("Université de Test 1"))
                .andExpect(jsonPath("$[1].nomUniversite").value("Université de Test 2"));

        verify(universiteService, times(1)).retrieveAllUniversites();
    }

    @Test
    void testAddUniversite() throws Exception {
        Universite newUniversite = new Universite(1L, "New Universite", "New Address");

        when(universiteService.addUniversite(any(Universite.class))).thenReturn(newUniversite);

        mockMvc.perform(post("/universite/add-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomUniversite\": \"New Universite\", \"adresse\": \"New Address\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite").value("New Universite"))
                .andExpect(jsonPath("$.adresse").value("New Address"));

        verify(universiteService, times(1)).addUniversite(any(Universite.class));
    }

    @Test
    void testRetrieveUniversite() throws Exception {
        Universite universite = new Universite(1L, "Université de Test", "Adresse de Test");

        when(universiteService.retrieveUniversite(1L)).thenReturn(universite);

        mockMvc.perform(get("/universite/retrieve-universite/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite").value("Université de Test"))
                .andExpect(jsonPath("$.adresse").value("Adresse de Test"));

        verify(universiteService, times(1)).retrieveUniversite(1L);
    }

    @Test
    void testRemoveUniversite() throws Exception {
        doNothing().when(universiteService).removeUniversite(1L);

        mockMvc.perform(delete("/universite/remove-universite/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(universiteService, times(1)).removeUniversite(1L);
    }

    @Test
    void testModifyUniversite() throws Exception {
        Universite modifiedUniversite = new Universite(1L, "Modified Universite", "Modified Address");

        when(universiteService.modifyUniversite(any(Universite.class))).thenReturn(modifiedUniversite);

        mockMvc.perform(put("/universite/modify-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idUniversite\": 1, \"nomUniversite\": \"Modified Universite\", \"adresse\": \"Modified Address\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite").value("Modified Universite"))
                .andExpect(jsonPath("$.adresse").value("Modified Address"));

        verify(universiteService, times(1)).modifyUniversite(any(Universite.class));
    }
}

