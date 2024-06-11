package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.shop.core.mapper.ProduktBoMapper;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.persistence.dao.ProduktRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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
     * Lists all products.
     *
     * @return list of products
     */
    public List<ProduktBo> getAllProduktBo() {

        return ProduktBoMapper.fromEntityList(produktDao.findAll());
    }

    /**
     * Searches for products by a given name.
     *
     * @param name name of products to search for
     * @return list of products found
     */
    public List<ProduktBo> findAllProduktBo(@NotNull String name) {

        return ProduktBoMapper.fromEntityList(produktDao.findByName(name));
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
                .orElseThrow(ProduktNotFoundException::new));
    }
}