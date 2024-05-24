package de.bund.bva.isyfact.shop.core;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.core.impl.ProduktVerwaltungImpl;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;

import java.util.List;

/**
 * This interface supplies facade methods for the core package.
 * For an implementation of this interface:
 *
 * @see ProduktVerwaltungImpl
 */
public interface ProduktVerwaltung {

    /**
     * updates the properties of the given product.
     * @param produktBo
     * @return
     */
    ProduktBo updateProduktBo(ProduktBo produktBo);
}
