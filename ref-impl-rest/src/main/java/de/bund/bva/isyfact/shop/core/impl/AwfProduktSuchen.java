package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.persistence.mapper.ProduktEntityMapper;
import de.bund.bva.isyfact.shop.persistence.dao.ProduktRepository;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwfProduktSuchen {

    /**
     * This constructor injects the required dependencies.
     * @param produktDao the repository for accessing the Produkt entity.
     */
    public AwfProduktSuchen(ProduktRepository produktDao) {
        this.produktDao = produktDao;
    }

    private final ProduktRepository produktDao;

    /**
     * Searches for Produkt entities by a given name.
     * If no such name is passed, all Produkt entities are returned, without any restriction.
     *
     * @param name name of Produkt entities to search for
     * @return list of ProduktBo business objects found
     */
    public List<ProduktBo> findAllProdukteByName(String name) {

        if (name == null) {
            return ProduktEntityMapper.toBoList(produktDao.findAll());
        } else {
            return ProduktEntityMapper.toBoList(produktDao.findByName(name));
        }
    }

    /**
     * Searches for the Produkt entity with a given id.
     *
     * @param id the id we received in the Produkt entity Controller class.
     * @return ProduktBo the business object with the given id
     * @throws ProduktNotFoundException if no such Produkt entity exists.
     */
    public ProduktBo getProduktById(long id) throws ProduktNotFoundException {
        return ProduktEntityMapper.toBo(produktDao.findById(id)
                .orElseThrow(ProduktNotFoundException::new));
    }
}