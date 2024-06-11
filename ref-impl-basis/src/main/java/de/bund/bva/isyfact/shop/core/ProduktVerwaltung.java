package de.bund.bva.isyfact.shop.core;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.core.impl.ProduktVerwaltungImpl;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;

import java.util.List;

/**
 * This interface supplies facade methods for the core package.
 * For an implementation of this interface
 *
 * @see ProduktVerwaltungImpl
 */
public interface ProduktVerwaltung {

    /**
     * Searches for products by a given name.
     * If no such name is passed, all products are returned, without any restriction.
     *
     * @param name name of products to search for
     * @return list of products found
     */
    List<ProduktBo> findAllProduktBo(String name);

    /**
     * Searches for the product with a given id.
     *
     * @param id the id we received in the ProductController class.
     * @return the Produkt with the given id
     * @throws ProduktNotFoundException if no such produkt exists
     */
    ProduktBo findProduktBoById(long id) throws ProduktNotFoundException;
}
