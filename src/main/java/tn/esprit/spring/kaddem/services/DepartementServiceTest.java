import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

public class DepartementServiceTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllDepartements() {
        // Mock data
        List<Departement> departements = new ArrayList<>();
        departements.add(new Departement(1, "IT"));
        departements.add(new Departement(2, "HR"));

        // Mocking repository behavior
        when(departementRepository.findAll()).thenReturn(departements);
        List <Departement> result = departementService.retrieveAllDepartements();

        // Verify that the service method returns the expected result
        assertEquals(2, result.size());
        /*assertEquals("IT", result.get(0).getName());
        assertEquals("HR", result.get(1).getName());*/
    }
}
