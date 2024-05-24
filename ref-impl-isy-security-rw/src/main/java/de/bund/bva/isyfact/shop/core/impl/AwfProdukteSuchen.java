package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.shop.core.mapper.ProduktBoMapper;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.persistence.dao.ProduktRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * This class provides search methods for finding products in the underlying database.
 */
@Service
public class AwfProdukteSuchen {

    /**
     * This constructor injects the required dependencies.
     * @param produktDao the repository for accessing the Produkt entity.
     */
    public AwfProdukteSuchen(ProduktRepository produktDao) {
        this.produktDao = produktDao;
    }

    private final ProduktRepository produktDao;

    /**
     * Searches for products by a given name.
     * If no such name is passed, all products are returned, without any restriction.
     *
     * @param name
     * @return list of products
     */
    public List<ProduktBo> findAllProduktBo(String name) {

        if (name == null) {
            return ProduktBoMapper.fromEntityList(produktDao.findAll());
        } else {
            return ProduktBoMapper.fromEntityList(produktDao.findByName(name));
        }
    }

    /**
     * Searches for the product with a given id.
     *
     * @param id the id we received in the Produkt Controller class.
     * @return the product with the given id
     * @throws ProduktNotFoundException if no such Produkt exists.
     */
    public ProduktBo findProduktBoById(long id) throws ProduktNotFoundException {
        return ProduktBoMapper.fromEntity(produktDao.findById(id)
                .orElseThrow(() -> new ProduktNotFoundException()));
    }
}