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
 * - PUT for updating a Produkt is secured with "PRIV_Recht_B"
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
     * updates the properties of the given product.
     * @param produktBo
     * @return
     */
    @PutMapping("/produkte")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured("PRIV_Recht_B")
    public ResponseEntity<ProduktBo> updateProduktBo(@RequestBody ProduktBo produktBo) throws ProduktNotFoundException {
        produktBo = produktVerwaltung.updateProduktBo(produktBo);
        return new ResponseEntity<>(produktBo, HttpStatus.OK);
    }
}
