package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.persistence.mapper.ProduktEntityMapper;
import de.bund.bva.isyfact.shop.persistence.dao.ProduktRepository;
import de.bund.bva.isyfact.shop.persistence.entity.ProduktEntity;
import org.springframework.stereotype.Service;

/*
 * This class provides a method for updating a given Produkt entity in the underlying database.
 */
@Service
public class AwfProduktAktualisieren {

    /**
     * This constructor injects the required dependencies.
     * @param produktDao the repository for accessing the Produkt entity.
     */
    public AwfProduktAktualisieren(ProduktRepository produktDao) {
        this.produktDao = produktDao;
    }

    private final ProduktRepository produktDao;

    /**
     * Updates the corresponding Produkt entity in the underlying database
     * with a given ProduktBo business object.
     * @param produkt ProduktBo business object with new attributes to be updated
     * @return ProduktBo the updated business object, as in database
     */
    public ProduktBo updateProdukt(ProduktBo produkt) {
        ProduktEntity product = produktDao.save(ProduktEntityMapper.fromBo(produkt));
        return ProduktEntityMapper.toBo(product);
    }

}