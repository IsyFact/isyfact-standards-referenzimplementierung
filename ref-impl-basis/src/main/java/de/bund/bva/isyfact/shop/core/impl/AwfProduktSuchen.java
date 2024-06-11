package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.core.mapper.ProduktBoMapper;
import de.bund.bva.isyfact.shop.persistence.dao.ProduktRepository;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwfProduktSuchen {

    /**
     * This constructor injects the required dependencies.
     * @param produktDao the repository for acessing the Produkt entity.
     */
    public AwfProduktSuchen(ProduktRepository produktDao) {
        this.produktDao = produktDao;
    }

    private final ProduktRepository produktDao;

    /**
     * Searches for products by a given name.
     * If no such name is passed, all products are returned, without any restriction.
     *
     * @param name name of products to search for
     * @return list of products found
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
     * @return the Produkt with the given id
     * @throws ProduktNotFoundException if no such Produkt exists.
     */
    public ProduktBo findProduktBoById(long id) throws ProduktNotFoundException {
        return ProduktBoMapper.fromEntity(produktDao.findById(id)
                .orElseThrow(ProduktNotFoundException::new));
    }
}