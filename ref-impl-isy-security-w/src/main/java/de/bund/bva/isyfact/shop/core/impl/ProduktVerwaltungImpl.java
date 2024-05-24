package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.security.oauth2.client.annotation.Authenticate;
import de.bund.bva.isyfact.shop.core.ProduktVerwaltung;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * This class implements the facade methods of the ProduktVerwaltung interface.
 */
@Component
public class ProduktVerwaltungImpl implements ProduktVerwaltung {

    /**
     * This constructor injects the required dependencies.
     * @param awfProdukteAktualisieren the Awf call to which we delegate.
     */
    public ProduktVerwaltungImpl (AwfProdukteAktualisieren awfProdukteAktualisieren) {
         this.awfProdukteAktualisieren = awfProdukteAktualisieren;
    }
    private AwfProdukteAktualisieren awfProdukteAktualisieren;

    /**
     * updates the properties of the given product.
     * @param produktBo
     * @return updated produktBo
     */
    @Override
    @Authenticate("reg-client-a") // has "PRIV_Recht_A"
    public ProduktBo updateProduktBo(ProduktBo produktBo)  {
        return awfProdukteAktualisieren.updateProduktBo(produktBo); // calls REST API of 'ref-impl-security-rw'
    }

}
