import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    @BeforeEach
    void initializeMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void retrieveAllContrats_ShouldReturnAllContrats() {
        List<Contrat> expectedContrats = new ArrayList<>();
        expectedContrats.add(new Contrat());
        expectedContrats.add(new Contrat());

        when(contratRepository.findAll()).thenReturn(expectedContrats);

        List<Contrat> actualContrats = contratService.retrieveAllContrats();

        assertNotNull(actualContrats);
        assertEquals(2, actualContrats.size());
        verify(contratRepository).findAll();
    }

    @Test
    void addContrat_ShouldSaveAndReturnContrat() {
        Contrat newContrat = new Contrat();
        when(contratRepository.save(any(Contrat.class))).thenReturn(newContrat);

        Contrat savedContrat = contratService.addContrat(new Contrat());

        assertNotNull(savedContrat);
        verify(contratRepository).save(any(Contrat.class));
    }

    @Test
    void retrieveContrat_ShouldReturnContratGivenId() {
        Integer contratId = 1;
        Optional<Contrat> expectedContrat = Optional.of(new Contrat());
        when(contratRepository.findById(contratId)).thenReturn(expectedContrat);

        Contrat actualContrat = contratService.retrieveContrat(contratId);

        assertNotNull(actualContrat);
        verify(contratRepository).findById(contratId);
    }

    @Test
    void removeContrat_ShouldDeleteContratById() {
        Integer contratId = 1;
        Contrat existingContrat = new Contrat();
        when(contratRepository.findById(contratId)).thenReturn(Optional.of(existingContrat));
        doNothing().when(contratRepository).delete(existingContrat);

        contratService.removeContrat(contratId);

        verify(contratRepository).findById(contratId);
        verify(contratRepository).delete(existingContrat);
    }
}
