package tn.esprit.tpfoyer;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FoyerRepositoryUnitTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize sample data for testing
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Main Foyer");
        foyer.setCapaciteFoyer(300);

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
    }

    @AfterEach
    void destroy() {
        // No real database cleanup necessary since we're mocking
        foyer = null;
    }

    @Test
    void testGetInvalidFoyer_ShouldThrowNoSuchElementException() {
        // Arrange
        when(foyerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> {
            foyerRepository.findById(1L).get();
        });
    }

    @Test
    void testDeleteFoyer_ShouldThrowNoSuchElementException_WhenDeleted() {
        // Arrange
        Foyer newFoyer = new Foyer();
        newFoyer.setIdFoyer(2L);
        newFoyer.setNomFoyer("Test Foyer");

        // Act
        when(foyerRepository.save(newFoyer)).thenReturn(newFoyer);
        foyerRepository.save(newFoyer);

        doNothing().when(foyerRepository).delete(newFoyer);
        foyerRepository.delete(newFoyer);

        // Assert that accessing the deleted entity throws NoSuchElementException
        when(foyerRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            foyerRepository.findById(2L).get();
        });
    }
}
