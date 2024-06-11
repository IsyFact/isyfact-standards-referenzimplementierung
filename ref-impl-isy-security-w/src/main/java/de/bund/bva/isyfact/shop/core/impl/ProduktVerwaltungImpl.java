package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.security.oauth2.client.annotation.Authenticate;
import de.bund.bva.isyfact.shop.core.ProduktVerwaltung;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.springframework.stereotype.Component;


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
    private final AwfProdukteAktualisieren awfProdukteAktualisieren;

    /**
     * Updates the properties of the given product
     * by self-authenticating as reg-client-a and
     * calling 'Update Produkt' of RestApplicationRW in module ref-impl-security-rw.
     * @param produktBo Produkt business object with new attributes to be updated
     * @return the updated Produkt business object, as in database
     */
    @Override
    @Authenticate("reg-client-a") // has "PRIV_Recht_A"
    public ProduktBo updateProduktBo(ProduktBo produktBo)  {
        return awfProdukteAktualisieren.updateProduktBo(produktBo); // calls REST API of 'ref-impl-security-rw'
    }

}
