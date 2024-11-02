package tn.esprit.tpfoyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.control.FoyerRestController;
import tn.esprit.tpfoyer.entity.Foyer;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import tn.esprit.tpfoyer.control.FoyerRestController;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.OK;


class FoyerRestControllerUnitTest {



    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private FoyerRestController foyerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFoyers() {
        // Mock data
        Foyer foyer1 = new Foyer(1L, "Foyer A", 300, null, null);
        Foyer foyer2 = new Foyer(2L, "Foyer B", 400, null, null);
        List<Foyer> foyerList = Arrays.asList(foyer1, foyer2);

        // Mocking behavior
        when(foyerService.retrieveAllFoyers()).thenReturn(foyerList);

        // Perform the test
        List<Foyer> result = foyerController.getFoyers();

        // Verify the interactions
        verify(foyerService, times(1)).retrieveAllFoyers();

        // Assertions
        assertEquals(2, result.size());
    }

    @Test
    void testAddFoyer() {
        // Mock data for the Foyer object
        Foyer foyerToAdd = new Foyer(null, "Test Foyer", 100, null, null);
        Foyer addedFoyer = new Foyer(1L, "Test Foyer", 100, null, null); // Assuming the ID will be generated

        // Mocking behavior
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(addedFoyer);

        // Act: Call the addFoyer method directly
        Foyer result = foyerController.addFoyer(foyerToAdd);

        // Assert: Verify the results
        assertNotNull(result, "Added foyer should not be null");
        assertEquals("Test Foyer", result.getNomFoyer(), "Foyer name should match the added value");
        assertEquals(100, result.getCapaciteFoyer(), "Foyer capacity should match the added value");
        assertEquals(1L, result.getIdFoyer(), "Foyer ID should match the generated ID");

        // Verify the interactions with the service
        verify(foyerService, times(1)).addFoyer(any(Foyer.class));
    }

    @Test
    void testModifyFoyer() {
        // Mock data for the Foyer object
        Foyer foyerToModify = new Foyer(1L, "Updated Foyer", 150, null, null);
        Foyer modifiedFoyer = new Foyer(1L, "Updated Foyer", 150, null, null); // Assuming the modified entity is returned

        // Mocking behavior
        when(foyerService.modifyFoyer(any(Foyer.class))).thenReturn(modifiedFoyer);

        // Act: Call the modifyFoyer method directly
        Foyer result = foyerController.modifyFoyer(foyerToModify);

        // Assert: Verify the results
        assertNotNull(result, "Modified foyer should not be null");
        assertEquals("Updated Foyer", result.getNomFoyer(), "Foyer name should match the updated value");
        assertEquals(150, result.getCapaciteFoyer(), "Foyer capacity should match the updated value");
        assertEquals(1L, result.getIdFoyer(), "Foyer ID should match the modified ID");

        // Verify the interactions with the service
        verify(foyerService, times(1)).modifyFoyer(any(Foyer.class));
    }

    @Test
    void testRetrieveFoyer() {
        // Mock data for the Foyer object
        Foyer expectedFoyer = new Foyer(1L, "Test Foyer", 100, null, null);

        // Mocking behavior
        when(foyerService.retrieveFoyer(1L)).thenReturn(expectedFoyer);

        // Act: Call the retrieveFoyer method directly
        Foyer result = foyerController.retrieveFoyer(1L);

        // Assert: Verify the results
        assertNotNull(result, "Retrieved foyer should not be null");
        assertEquals("Test Foyer", result.getNomFoyer(), "Foyer name should match the expected value");
        assertEquals(100, result.getCapaciteFoyer(), "Foyer capacity should match the expected value");
        assertEquals(1L, result.getIdFoyer(), "Foyer ID should match the expected ID");

        // Verify the interactions with the service
        verify(foyerService, times(1)).retrieveFoyer(1L);
    }




    @Test
    void testRemoveFoyer() {
        // Arrange
        Long foyerId = 8L;

        // Act
        ResponseEntity<Void> response = foyerController.removeFoyer(foyerId);

        // Assert
        verify(foyerService, times(1)).removeFoyer(foyerId);
        assertEquals(OK, response.getStatusCode()); // Checks if the status is 200 OK
    }
}
