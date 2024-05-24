package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.core.mapper.ProduktBoMapper;
import de.bund.bva.isyfact.shop.persistence.dao.ProduktRepository;
import de.bund.bva.isyfact.shop.persistence.entity.Produkt;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.springframework.stereotype.Service;

/*
 * This class provides a method for updating a given product in the underlying database.
 */
@Service
public class AwfProdukteAktualisieren {

    /**
     * This constructor injects the required dependencies.
     * @param produktDao the repository for accessing the Produkt entity.
     */
    public AwfProdukteAktualisieren(ProduktRepository produktDao) {
        this.produktDao = produktDao;
    }

    private final ProduktRepository produktDao;

    /**
     * Updates the corresponding Produkt entity in the underyling database
     * with a given Produkt business object.
     * @param produktBo
     * @return the updated Produkt business object
     * @throws ProduktNotFoundException, if no such entity exists
     */
    public ProduktBo updateProduktBo(ProduktBo produktBo) throws ProduktNotFoundException {
        Produkt product = produktDao.save(ProduktBoMapper.toEntity(produktBo));
        return ProduktBoMapper.fromEntity(product);
    }

}