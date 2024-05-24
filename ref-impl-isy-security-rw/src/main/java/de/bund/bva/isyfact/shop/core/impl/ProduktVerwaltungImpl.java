package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.shop.core.ProduktVerwaltung;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * This class implements the facade methods of the ProduktVerwaltung interface.
 */
@Component
public class ProduktVerwaltungImpl implements ProduktVerwaltung {

    /**
     * This constructor injects the required dependencies.
     * @param awfProduktSuchen the Awf call to which we delegate.
     * @param awfProduktAktualisieren the Awf call to which we delegate
     */
    public ProduktVerwaltungImpl(AwfProdukteSuchen awfProduktSuchen, AwfProdukteAktualisieren awfProduktAktualisieren) {
        this.awfProduktSuchen = awfProduktSuchen;
        this.awfProdukteAktualisieren = awfProduktAktualisieren;
    }
    private final AwfProdukteSuchen awfProduktSuchen;
    private final AwfProdukteAktualisieren awfProdukteAktualisieren;

    /**
     * Searches for products by a given name.
     * If no such name is passed, all products are returned, without any restriction.
     * @param name
     * @return list of products
     */
    @Override
    public List<ProduktBo> findAllProduktBo(String name) {
        return awfProduktSuchen.findAllProduktBo(name);
    }

    /**
     * Searches for the product with a given id.
      *
     * @param id the id we received in the ProductController class.
     * @return the Produkt with the given id
     * @throws ProduktNotFoundException if no such Produkt exists
     */
    @Override
    public ProduktBo findProduktBoById(long id) throws ProduktNotFoundException {
        return awfProduktSuchen.findProduktBoById(id);
    }

    /**
     * Updates the corresponding Produkt entity in the underyling database
     * with a given Produkt business object.
     * @param produktBo
     * @return the updated Produkt business object
     * @throws ProduktNotFoundException, if no such entity exists
     */
    @Override
    public ProduktBo updateProduktBo(ProduktBo produktBo) throws ProduktNotFoundException {
        return awfProdukteAktualisieren.updateProduktBo(produktBo);
    }

}
