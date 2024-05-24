package de.bund.bva.isyfact.shop.core;

import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.core.impl.ProduktVerwaltungImpl;

import java.util.List;

/**
 * This interface supplies facade methods for the core package.
 * For an implementation of this interface:
 *
 * @see ProduktVerwaltungImpl
 */
public interface ProduktVerwaltung {

    /**
     * Searches for products by a given name.
     * If no such name is passed, all products are returned, without any restriction.
     *
     * @param name
     * @return list of products
     */
    List<ProduktBo> findAllProduktBo(String name);

    /**
     * Searches for the product with a given id.
     *
     * @param id the id we received in the ProductController class.
     * @return the Produkt with the given id
     * @throws ProduktNotFoundException if no such Produkt exists
     */
    ProduktBo findProduktBoById(long id) throws ProduktNotFoundException;

    /**
     * Updates the corresponding Produkt entity in the underyling database
     * with a given Produkt business object.
     * @param produktBo
     * @return the updated Produkt business object
     * @throws ProduktNotFoundException, if no such entity exists
     */
    ProduktBo updateProduktBo(ProduktBo produktBo) throws ProduktNotFoundException;
}
