package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChambreTests {

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @Mock
    private ChambreRepository chambreRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllChambres_ShouldReturnListOfChambres() {
        // Arrange
        Chambre chambre1 = new Chambre(); // Add properties as necessary
        Chambre chambre2 = new Chambre(); // Add properties as necessary
        List<Chambre> chambres = Arrays.asList(chambre1, chambre2);
        when(chambreRepository.findAll()).thenReturn(chambres);

        // Act
        List<Chambre> result = chambreService.retrieveAllChambres();

        // Assert
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void retrieveChambre_ShouldReturnChambre() {
        // Arrange
        Long chambreId = 1L;
        Chambre chambre = new Chambre(); // Add properties as necessary
        when(chambreRepository.findById(chambreId)).thenReturn(Optional.of(chambre));

        // Act
        Chambre result = chambreService.retrieveChambre(chambreId);

        // Assert
        assertNotNull(result);
        assertEquals(chambre, result);
        verify(chambreRepository, times(1)).findById(chambreId);
    }

    @Test
    void addChambre_ShouldReturnSavedChambre() {
        // Arrange
        Chambre chambre = new Chambre(); // Add properties as necessary
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // Act
        Chambre result = chambreService.addChambre(chambre);

        // Assert
        assertNotNull(result);
        assertEquals(chambre, result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void modifyChambre_ShouldReturnModifiedChambre() {
        // Arrange
        Chambre chambre = new Chambre(); // Add properties as necessary
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // Act
        Chambre result = chambreService.modifyChambre(chambre);

        // Assert
        assertNotNull(result);
        assertEquals(chambre, result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void removeChambre_ShouldCallDeleteById() {
        // Arrange
        Long chambreId = 1L;

        // Act
        chambreService.removeChambre(chambreId);

        // Assert
        verify(chambreRepository, times(1)).deleteById(chambreId);
    }

    @Test
    void recupererChambresSelonTyp_ShouldReturnListOfChambres() {
        // Arrange
        TypeChambre typeChambre = TypeChambre.SIMPLE; // Replace SOME_TYPE with a valid enum value
        Chambre chambre1 = new Chambre(); // Initialize properties as necessary
        List<Chambre> chambres = Arrays.asList(chambre1);
        when(chambreRepository.findAllByTypeC(typeChambre)).thenReturn(chambres);

        // Act
        List<Chambre> result = chambreService.recupererChambresSelonTyp(typeChambre);

        // Assert
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findAllByTypeC(typeChambre);
    }


    @Test
    void trouverchambreSelonEtudiant_ShouldReturnChambre() {
        // Arrange
        long cin = 123456;
        Chambre chambre = new Chambre(); // Add properties as necessary
        when(chambreRepository.trouverChselonEt(cin)).thenReturn(chambre);

        // Act
        Chambre result = chambreService.trouverchambreSelonEtudiant(cin);

        // Assert
        assertNotNull(result);
        assertEquals(chambre, result);
        verify(chambreRepository, times(1)).trouverChselonEt(cin);
    }
}
