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
     * @param name of products to search for
     * @return list of ProduktBo found
     */
    List<ProduktBo> findAllProdukteByName(String name);

    /**
     * Searches for the product with a given id.
     *
     * @param produktId under which the product is stored in the database
     * @return the ProduktBo with the given id
     * @throws ProduktNotFoundException if no such produkt exists
     */
    ProduktBo getProduktById(long produktId) throws ProduktNotFoundException;

    /**
     * Updates the product in the underlying database
     * with a given ProduktBo business object.
     * @param produkt ProduktBo business object with new attributes to be updated
     * @return the updated ProduktBo business object, as in database
     */
    ProduktBo updateProdukt(ProduktBo produkt);

}
