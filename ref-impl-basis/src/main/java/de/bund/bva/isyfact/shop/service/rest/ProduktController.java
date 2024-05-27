package de.bund.bva.isyfact.shop.service.rest;


import de.bund.bva.isyfact.shop.core.ProduktVerwaltung;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for the produkte resource.
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
     * Searches for a product with a given name.
     * If no such substring is passed, all products are returned, without any restriction.
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
     * Searches for the product with a given id.
     * If no such product exists a ProduktNotFoundException is thrown.
     *
     * @param id
     * @return the Produkt with the given id
     * @throws ProduktNotFoundException
     */
    @GetMapping("/produkte/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<ProduktBo> findProduktBoById(@PathVariable("id") long id) throws ProduktNotFoundException {

        ProduktBo produktBo = produktVerwaltung.findProduktBoById(id);
        return new ResponseEntity<>(produktBo, HttpStatus.OK);
    }
}
