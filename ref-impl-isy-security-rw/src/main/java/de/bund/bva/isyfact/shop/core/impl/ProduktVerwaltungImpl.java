package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.security.core.Security;
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
     * @param awfProduktSuchen the Awf call to which we delegate.
     * @param awfProduktAktualisieren the Awf call to which we delegate
     */
    public ProduktVerwaltungImpl(AwfProdukteSuchen awfProduktSuchen,
                                 AwfProdukteAktualisieren awfProduktAktualisieren,
                                 Security security) {
        this.awfProduktSuchen = awfProduktSuchen;
        this.awfProdukteAktualisieren = awfProduktAktualisieren;
        this.security = security;
    }
    private final AwfProdukteSuchen awfProduktSuchen;
    private final AwfProdukteAktualisieren awfProdukteAktualisieren;
    private final Security security;

    /**
     * Searches for Produkt business objects with a given name.
     * If no such name is passed:
     * - For users in department (Abteilung) 'Zentrale':
     *   all Product business objects are returned, without any restriction.
     * - For all other users:
     *   ProduktNotFoundException
     *
     * @param name  product name to search for
     * @return product list
     * @throws ProduktNotFoundException, if no name is given (by users NOT in 'Zentrale')
     */
    @Override
    public List<ProduktBo> findAllProduktBo(String name) throws ProduktNotFoundException {

            // Usage of Berechtigungsmanager for a custom attribute check:
            // use case:
            // - Only users belonging to department (Abteilung) 'Zentrale' can list all products
            //   by NOT specifying a product name.
            // - All other users have to specify a name and get a 'Produkt Not Found' error otherwise.
            if (name == null || name.isEmpty()) {

                // retrieve attribute (optional as in KeyCloak mapper)
                String abteilung  = String.valueOf(
                        security.getBerechtigungsmanager().getTokenAttribute("abteilung"));

                // use case:
                if (abteilung != null && abteilung.equals("Zentrale")) {
                    return awfProduktSuchen.getAllProduktBo();
                } else {
                    throw new ProduktNotFoundException();
                }
        } else {
                return awfProduktSuchen.findAllProduktBo(name);
            }
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
     * @param produktBo Produkt business object with new attributes to be updated
     * @return the updated Produkt business object, as in database
     */
    @Override
    public ProduktBo updateProduktBo(ProduktBo produktBo) {
        return awfProdukteAktualisieren.updateProduktBo(produktBo);
    }

}
