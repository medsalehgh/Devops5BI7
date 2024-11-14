package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L); // Use setIdEtudiant
        etudiant.setCinEtudiant(12345678L);
        etudiant.setNomEtudiant("John"); // Use setNomEtudiant
        etudiant.setPrenomEtudiant("Doe"); // Use setPrenomEtudiant
    }

    @Test
    void retrieveAllEtudiants_ShouldReturnEtudiantsList() {
        // Arrange
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant));

        // Act
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();

        // Assert
        assertNotNull(etudiants);
        assertEquals(1, etudiants.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void retrieveEtudiant_ShouldReturnEtudiant_WhenIdExists() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantService.retrieveEtudiant(1L);

        // Assert
        assertNotNull(result);
        assertEquals(etudiant.getNomEtudiant(), result.getNomEtudiant()); // Use getNomEtudiant
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void addEtudiant_ShouldSaveAndReturnEtudiant() {
        // Arrange
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant()); // Use getNomEtudiant
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void modifyEtudiant_ShouldUpdateAndReturnEtudiant() {
        // Arrange
        etudiant.setNomEtudiant("Jane"); // Use setNomEtudiant
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.modifyEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.getNomEtudiant()); // Use getNomEtudiant
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void removeEtudiant_ShouldDeleteEtudiant_WhenIdExists() {
        // Act
        etudiantService.removeEtudiant(1L);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void recupererEtudiantParCin_ShouldReturnEtudiant_WhenCinExists() {
        // Arrange
        when(etudiantRepository.findEtudiantByCinEtudiant(12345678L)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.recupererEtudiantParCin(12345678L);

        // Assert
        assertNotNull(result);
        assertEquals(12345678L, result.getCinEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(12345678L);
    }
}
