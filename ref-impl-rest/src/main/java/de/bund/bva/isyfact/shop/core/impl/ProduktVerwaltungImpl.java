package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import de.bund.bva.isyfact.shop.core.ProduktVerwaltung;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * This class implements the facade methods of ProduktVerwaltung interface.
 */
@Component
public class ProduktVerwaltungImpl implements ProduktVerwaltung {

    /**
     * This constructor injects the required dependencies.
     * @param awfProduktSuchen the Awf call to which we delegate.
     */
    public ProduktVerwaltungImpl(AwfProduktSuchen awfProduktSuchen, AwfProduktAktualisieren awfProdukteAktualisieren) {
        this.awfProduktSuchen = awfProduktSuchen;
        this.awfProdukteAktualisieren = awfProdukteAktualisieren;
    }

    private final AwfProduktSuchen awfProduktSuchen;
    private final AwfProduktAktualisieren awfProdukteAktualisieren;

    /**
     * Searches for products by a given name.
     * If no such name is passed, all products are returned, without any restriction.
     * @param name name of products to search for
     * @return list of ProduktBo business objects found (empty, if no matches)
     */
    @Override
    public List<ProduktBo> findAllProdukteByName(String name) {
        return awfProduktSuchen.findAllProdukteByName(name);
    }

    /**
     * Searches for the product with a given produktId.
      *
     * @param produktId the produktId we received in the ProductController class.
     * @return ProduktBo the business object with the given produktId
     * @throws ProduktNotFoundException if no such Produkt entity exists
     */
    @Override
    public ProduktBo getProduktById(long produktId) throws ProduktNotFoundException {
        return awfProduktSuchen.getProduktById(produktId);
    }

    /**
     * Updates the corresponding ProduktBo entity in the underyling database
     * with a given ProduktBo business object.
     * @param produktBo ProduktBo business object with new attributes to be updated
     * @return the updated ProduktBo business object, as in database
     */
    @Override
    public ProduktBo updateProdukt(ProduktBo produktBo) {
        return awfProdukteAktualisieren.updateProdukt(produktBo);
    }

}
