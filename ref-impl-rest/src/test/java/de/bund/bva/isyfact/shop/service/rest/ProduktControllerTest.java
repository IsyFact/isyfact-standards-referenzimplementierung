package de.bund.bva.isyfact.shop.service.rest;

import de.bund.bva.isyfact.shop.RestApplication;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import de.bund.bva.isyfact.shop.service.rest.model.ProduktModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
            ProduktModel produktModel =
                    produktController.getProduktById(i).getBody();

            // then
            assertNotNull(produktModel);
            assertEquals(i, produktModel.getId()) ;
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
        assertThrows(ProduktNotFoundException.class, () ->
                produktController.getProduktById(idOutOfBounds));
    }

    /**
     * Positive test for the findAllProduktBo method.
     */
    @Test
    public void testFindAllProduktBo()  {

        // given
        String name = "Pellegrino";

        // when
        ResponseEntity<List<ProduktModel>> responseEntity = produktController.findAllProdukteByName(name, 10);
        List<ProduktModel> produktModelList = responseEntity.getBody();

        // then
        assertNotNull(produktModelList);
        assertEquals(1, produktModelList.size());
    }

    /**
     * Negative test for the findAllProduktBo method.
     */
    @Test
    public void testCannotFindAllProduktBo()  {

        // given
        String name = "Parmesan";

        // when
        ResponseEntity<List<ProduktModel>> responseEntity = produktController.findAllProdukteByName(name, 10);
        List<ProduktModel> produktModelList = responseEntity.getBody();

        // then
        assertNull(produktModelList);
    }
}
