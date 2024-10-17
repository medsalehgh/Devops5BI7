package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Main Foyer");
        foyer.setCapaciteFoyer(200);
    }

    @Test
    void retrieveAllFoyers_ShouldReturnFoyersList() {
        // Arrange
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer));

        // Act
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Assert
        assertNotNull(foyers);
        assertEquals(1, foyers.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void retrieveFoyer_ShouldReturnFoyer_WhenIdExists() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Act
        Foyer result = foyerService.retrieveFoyer(1L);

        // Assert
        assertNotNull(result);
        assertEquals(foyer.getNomFoyer(), result.getNomFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void addFoyer_ShouldSaveAndReturnFoyer() {
        // Arrange
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer result = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals("Main Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void modifyFoyer_ShouldUpdateAndReturnFoyer() {
        // Arrange
        foyer.setNomFoyer("Updated Foyer");
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer result = foyerService.modifyFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void removeFoyer_ShouldDeleteFoyer_WhenIdExists() {
        // Act
        foyerService.removeFoyer(1L);

        // Assert
        verify(foyerRepository, times(1)).deleteById(1L);
    }
}
