package de.bund.bva.isyfact.shop.service.rest.api;

import de.bund.bva.isyfact.shop.core.adapter.ProdukteAdapter;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Smoketest for checking get-product requests:
 * - sends HTTP requests to RestApplication and
 * - asserts expected response
 * <p>
 * Note: ref-impl-basis RestApplication needs to be running!
 */
public class ProdukteAdapterTest {


    private final ProdukteAdapter produkteAdapter = new ProdukteAdapter();


    /**
     * Sends get request to the produkte resource
     * of our isifact-standards-tutorial application, running on localhost:8081.
     */
    @Test
    public void testGetProduktBoById() throws ProduktNotFoundException {

        // given
        long produktId = 1L;

        // when
        ProduktBo produktBo = produkteAdapter.getProduktBoById(produktId);

        // then
        assertEquals("{ id: 1, name:'Emmentaler', beschreibung:'Hartkäse' }",
                produktBo.toString());
    }

    /**
     * Sends get request to the produkte resource
     * of our isifact-standards-tutorial application, running on localhost:8081.
     */
    @Test
    public void testFindProduktBoByName() throws ProduktNotFoundException {

        // given
        String name = "Emmentaler";

        // when
        List<ProduktBo> produktBoList = produkteAdapter.findAllProduktBoByName(name);

        // then
        assertNotNull(produktBoList);
        assertEquals(1,produktBoList.size());
        assertEquals("[{ id: 1, name:'Emmentaler', beschreibung:'Hartkäse' }]",
                produktBoList.toString());
    }

}
