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
     * Searches for Produkt business objects with a given name.
     * If no such name is passed:
     * - For users in department (Abteilung) 'Zentrale':
     *   all Product business objects are returned, without any restriction.
     * - For all other users:
     *   ProduktNotFoundException
     *
     * @param name name of products to search for
     * @return list of products found
     * @throws ProduktNotFoundException, if no name is given (by users NOT in 'Zentrale')
     */
    List<ProduktBo> findAllProduktBo(String name) throws ProduktNotFoundException;

    /**
     * Searches for the product with a given id.
     *
     * @param id the id we received in the ProductController class.
     * @return the Produkt with the given id
     * @throws ProduktNotFoundException if no such Produkt exists
     */
    ProduktBo findProduktBoById(long id) throws ProduktNotFoundException;

    /**
     * Updates the corresponding Produkt entity in the underlying database
     * with a given Produkt business object.
     * @param produktBo Produkt business object with new attributes to be updated
     * @return the updated Produkt business object, as in database
     */
    ProduktBo updateProduktBo(ProduktBo produktBo);
}
