package tn.esprit.tpfoyer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;

class ReservationTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        reservation = new Reservation();
        reservation.setIdReservation(1L);
        etudiant.setAnneeUniversitaire(new Date());
        etudiant.setEstValide(true);
        Set<Etudiant> etudiants = new HashSet<Etudiant>();
        etudiant.setEtudiants(etudiants);
    }

    @Test
    void retrieveAllReservations() {
        // Arrange
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        // Act
        List<Reservation> reservations = reservationService.retrieveAllReservations();

        // Assert
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    

    @Test
    void removeReservation() {
        // Act
        reservationService.removeReservation(1L);

        // Assert
        verify(reservationRepository, times(1)).deleteById(1L);
    }

    
}
