package tn.esprit.tpfoyer;

import tn.esprit.tpfoyer.control.FoyerRestController;
import tn.esprit.tpfoyer.entity.Foyer;

import tn.esprit.tpfoyer.service.IFoyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

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
