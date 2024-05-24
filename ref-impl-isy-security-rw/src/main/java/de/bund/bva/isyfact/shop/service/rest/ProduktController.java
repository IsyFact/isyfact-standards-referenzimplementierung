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
     * @param produktVerwaltung
     */
    public ProduktController(ProduktVerwaltung produktVerwaltung) {
        this.produktVerwaltung = produktVerwaltung;
    }

    private final ProduktVerwaltung produktVerwaltung;


    /**
     * Searches for Produkt business objects with a given name.
     * If no such name is passed, all Product business objects are returned, without any restriction.
     *
     * @param name
     * @return product list
     */
    @GetMapping("/produkte")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<ProduktBo>> findAllProduktBo(@RequestParam(required = false) String name) {

        List<ProduktBo> produktBoList = produktVerwaltung.findAllProduktBo(name);

        if (produktBoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(produktBoList, HttpStatus.OK);
    }

    /**
     * Searches for a Produkt business object with a given id.
     *
     * @param id
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
     * @param produktBo
     * @return the updated Produkt business object
     * @throws ProduktNotFoundException, if no such entity exists
     */
    @PutMapping("/produkte")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured("PRIV_Recht_A")
    public ResponseEntity<ProduktBo> updateProduktBo(@RequestBody ProduktBo produktBo) throws ProduktNotFoundException {
        produktBo = produktVerwaltung.updateProduktBo(produktBo);
        return new ResponseEntity<>(produktBo, HttpStatus.OK);
    }
}
