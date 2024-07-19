package de.bund.bva.isyfact.shop.service.rest;

import de.bund.bva.isyfact.shop.core.ProduktVerwaltung;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for the produkte resource.
 */
@RestController
@RequestMapping("/shop/api/v1")
public class ProduktController {

    /**
     * This constructor injects the required dependencies.
     * @param produktVerwaltung Interface for core functionality 'Produkt-Verwaltung'.
     */
    public ProduktController(ProduktVerwaltung produktVerwaltung) {
        this.produktVerwaltung = produktVerwaltung;
    }

    private final ProduktVerwaltung produktVerwaltung;

    /**
     * Searches for products with a given name (name is not unique).
     * If no match is found, HttpStatus.NO_CONTENT is returned.
     *
     * @param name name of products to search for
     * @return list of ProduktBo found or HttpStatus.NO_CONTENT
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
     * @param id database ID
     * @return the ProduktBo with the given id
     * @throws ProduktNotFoundException if no such product exists
     */
    @GetMapping("/produkte/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<ProduktBo> findProduktBoById(@PathVariable("id") long id) throws ProduktNotFoundException {

        ProduktBo produktBo = produktVerwaltung.findProduktBoById(id);
        return new ResponseEntity<>(produktBo, HttpStatus.OK);
    }
}
