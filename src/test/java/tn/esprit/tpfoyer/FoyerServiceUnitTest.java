package tn.esprit.tpfoyer;

import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FoyerServiceUnitTest {

    @Mock
    FoyerRepository foyerRepository;

    @InjectMocks
    FoyerServiceImpl foyerService;

    @BeforeEach
    public void setup() {
        // Initialization code if needed
    }

    @Test
    void testGetAllFoyers() {
        // Creating mock Foyer data
        Foyer foyer1 = new Foyer(1L, "Foyer A", 100, null, null);
        Foyer foyer2 = new Foyer(2L, "Foyer B", 150, null, null);

        // Defining behavior for the repository mock
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer1, foyer2));

        // Calling the service method
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Verifying the results
        assertEquals(2, foyers.size());
        assertEquals("Foyer A", foyers.get(0).getNomFoyer());
        assertEquals("Foyer B", foyers.get(1).getNomFoyer());
    }

    @Test
    void testGetFoyerById() {
        // Creating a mock Foyer
        Foyer foyer = new Foyer(3L, "Foyer C", 200, null, null);

        // Defining behavior for the repository mock
        when(foyerRepository.findById(3L)).thenReturn(Optional.of(foyer));

        // Calling the service method
        Foyer foundFoyer = foyerService.retrieveFoyer(3L);

        // Verifying the results
        assertNotNull(foundFoyer);
        assertEquals("Foyer C", foundFoyer.getNomFoyer());
    }

    @Test
    void testGetInvalidFoyerById() {
        // Defining behavior for the repository mock when ID not found
        when(foyerRepository.findById(4L)).thenThrow(new RuntimeException("Foyer Not Found with ID"));

        // Testing the exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            foyerService.retrieveFoyer(4L);
        });

        assertTrue(exception.getMessage().contains("Foyer Not Found with ID"));
    }

    @Test
    void testCreateFoyer() {
        // Creating a mock Foyer
        Foyer foyer = new Foyer(null, "Foyer D", 250, null, null);

        // Calling the service method
        foyerService.addFoyer(foyer);

        // Verifying that the save method was called
        verify(foyerRepository, times(1)).save(foyer);

        // Capturing the argument
        ArgumentCaptor<Foyer> foyerArgumentCaptor = ArgumentCaptor.forClass(Foyer.class);
        verify(foyerRepository).save(foyerArgumentCaptor.capture());

        Foyer savedFoyer = foyerArgumentCaptor.getValue();

        // Verifying the results
        assertNotNull(savedFoyer);
        assertEquals("Foyer D", savedFoyer.getNomFoyer());
    }

    @Test
    void testDeleteFoyer() {
        // Creating a mock Foyer
        Foyer foyer = new Foyer(5L, "Foyer E", 300, null, null);

        // Defining behavior for the repository mock
        when(foyerRepository.findById(5L)).thenReturn(Optional.of(foyer));

        // Calling the service method
        foyerService.removeFoyer(foyer.getIdFoyer());

        // Verifying that the deleteById method was called
        verify(foyerRepository, times(1)).deleteById(foyer.getIdFoyer());

        // Optional: capture the ID passed to deleteById for additional verification
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(foyerRepository).deleteById(idCaptor.capture());

        Long deletedFoyerId = idCaptor.getValue();

        // Verifying the results
        assertNotNull(deletedFoyerId);
        assertEquals(5L, deletedFoyerId);
    }


}
