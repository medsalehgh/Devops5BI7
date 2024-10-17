import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlocTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @Test
    public void testAddBloc() {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setNomBloc("A Block");
        bloc.setCapaciteBloc(100);

        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc createdBloc = blocService.addBloc(bloc);

        // Assert
        assertNotNull(createdBloc);
        assertEquals("A Block", createdBloc.getNomBloc());
        assertEquals(100, createdBloc.getCapaciteBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    public void testGetBlocById() {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("A Block");
        bloc.setCapaciteBloc(100);

        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Act
        Bloc foundBloc = blocService.getBlocById(1L);

        // Assert
        assertNotNull(foundBloc);
        assertEquals(1L, foundBloc.getIdBloc());
        assertEquals("A Block", foundBloc.getNomBloc());
    }

    @Test
    public void testUpdateBloc() {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("B Block");
        bloc.setCapaciteBloc(150);

        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc updatedBloc = blocService.updateBloc(bloc);

        // Assert
        assertNotNull(updatedBloc);
        assertEquals("B Block", updatedBloc.getNomBloc());
        assertEquals(150, updatedBloc.getCapaciteBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    public void testDeleteBloc() {
        // Arrange
        long blocId = 1L;
        doNothing().when(blocRepository).deleteById(blocId);

        // Act
        blocService.deleteBloc(blocId);

        // Assert
        verify(blocRepository, times(1)).deleteById(blocId);
    }
}
