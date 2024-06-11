package de.bund.bva.isyfact.shop.core;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.core.impl.ProduktVerwaltungImpl;

/**
 * This interface supplies facade methods for the core package.
 * For an implementation of this interface:
 *
 * @see ProduktVerwaltungImpl
 */
public interface ProduktVerwaltung {

    /**
     * Updates the properties of the given product
     * by self-authenticating as reg-client-a and
     * calling 'Update Produkt' of RestApplicationRW in module ref-impl-security-rw.
     * @param produktBo Produkt business object with new attributes to be updated
     * @return the updated Produkt business object, as in database
     */
    ProduktBo updateProduktBo(ProduktBo produktBo);
}
