package de.bund.bva.isyfact.shop.service.rest;

import de.bund.bva.isyfact.shop.core.ProduktVerwaltung;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for the produkte resource.
 * - All GET-Endpoints are public (not secured),
 * - PUT for updating a Produkt is secured with "PRIV_Recht_A"
 */

@RestController
@RequestMapping("/shop/api/v1")
public class ProduktController {

    /**
     * This constructor injects the required dependencies.
     * @param produktVerwaltung Interface for core functionality 'Produkt-Verwaltung'
     */
    public ProduktController(ProduktVerwaltung produktVerwaltung) {
        this.produktVerwaltung = produktVerwaltung;
    }

    private final ProduktVerwaltung produktVerwaltung;


    /**
     * Searches for products with a given name (name is not unique).
     * If no match is found, HttpStatus.NO_CONTENT is returned
     * If no such name is passed:
     * - For users in department (Abteilung) 'Zentrale':
     *   all Product business objects are returned, without any restriction.
     * - For all other users:
     *   ProduktNotFoundException
     *
     * @param name name of products to search for
     * @return product list or HttpStatus.NO_CONTENT
     * @throws ProduktNotFoundException, if no name is given (by users NOT in 'Zentrale')
     */
    @GetMapping("/produkte")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<ProduktBo>> findAllProduktBo(@RequestParam(required = false) String name)
            throws ProduktNotFoundException {

        List<ProduktBo> produktBoList = produktVerwaltung.findAllProduktBo(name);

        if (produktBoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(produktBoList, HttpStatus.OK);
    }

    /**
     * Searches for a Produkt business object with a given id.
     *
     * @param id database ID
     * @return the Produkt business object with the given id
     * @throws ProduktNotFoundException, if no such object exists
     */
    @GetMapping("/produkte/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<ProduktBo> findProduktBoById(@PathVariable("id") long id) throws ProduktNotFoundException {

        ProduktBo produktBo = produktVerwaltung.findProduktBoById(id);
        return new ResponseEntity<>(produktBo, HttpStatus.OK);
    }

    /**
     * Updates the corresponding Produkt entity in the underyling database
     * with a given Produkt business object.
     * @param produktBo Produkt business object with new attributes to be updated
     * @return the updated Produkt business object, as in database
     */
    @PutMapping("/produkte")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured("PRIV_Recht_A")
    public ResponseEntity<ProduktBo> updateProduktBo(@RequestBody ProduktBo produktBo) {
        produktBo = produktVerwaltung.updateProduktBo(produktBo);
        return new ResponseEntity<>(produktBo, HttpStatus.OK);
    }
}
