package de.bund.bva.isyfact.shop.service.rest;

import de.bund.bva.isyfact.shop.core.ProduktVerwaltung;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


/**
 * Controller class for the produkte resource.
 * - PUT for updating a Produkt is secured with "PRIV_Recht_B"
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
     * Updates the properties of the given product
     * by self-authenticating as reg-client-a and
     * calling 'Update Produkt' of RestApplicationRW in module ref-impl-security-rw.
     * @param produktBo Produkt business object with new attributes to be updated
     * @return the updated Produkt business object, as in database
     */
    @PutMapping("/produkte")
    @ResponseStatus(HttpStatus.OK)
    @Secured("PRIV_Recht_B")
    public ResponseEntity<ProduktBo> updateProduktBo(@RequestBody ProduktBo produktBo) {
        produktBo = produktVerwaltung.updateProduktBo(produktBo);
        return new ResponseEntity<>(produktBo, HttpStatus.OK);
    }
}
