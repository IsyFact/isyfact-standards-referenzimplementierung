package de.bund.bva.isyfact.shop.service.rest;

import de.bund.bva.isyfact.shop.RestApplication;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;


/**
 * Regression test for the methods of the ProduktController class
 */
@SpringBootTest(classes= RestApplication.class)
public class ProduktControllerTest {

    @Autowired
    ProduktController produktController;

    /**
     * Positive test for the findProduktBoById method.
     * @throws ProduktNotFoundException if no produktBo exists
     */
    @Test
    public void testFindProduktBoById() throws ProduktNotFoundException {

        for (long i = 1; i <= 3; i++) {

            // when
            ProduktBo produktBo =
                    produktController.findProduktBoById(i).getBody();

            // then
            assertNotNull(produktBo);
            assertEquals(i, produktBo.getId()) ;
        }
    }

    /**
     * Negative test for the findProduktBoById method.
    */
    @Test
    public void testCannotFindProduktBoById()  {

        // given
        long idOutOfBounds = 777;

        // when then
        assertThrows(ProduktNotFoundException.class, () -> {
            produktController.findProduktBoById(idOutOfBounds);
        });
    }

    /**
     * Positive test for the findAllProduktBo method.
     */
    @Test
    public void testFindAllProduktBo()  {

        // given
        String name = "Pellegrino";

        // when
        ResponseEntity<List<ProduktBo>> produktBoEntities = produktController.findAllProduktBo(name);
        List<ProduktBo> produktBoList = produktBoEntities.getBody();

        // then
        assertNotNull(produktBoList);
        assertEquals(1, produktBoList.size());
    }

    /**
     * Negative test for the findAllProduktBo method.
     */
    @Test
    public void testCannotFindAllProduktBo()  {

        // given
        String name = "Parmesan";

        // when
        ResponseEntity<List<ProduktBo>> produktBoEntities = produktController.findAllProduktBo(name);
        List<ProduktBo> produktBoList = produktBoEntities.getBody();

        // then
        assertNull(produktBoList);
    }
}
