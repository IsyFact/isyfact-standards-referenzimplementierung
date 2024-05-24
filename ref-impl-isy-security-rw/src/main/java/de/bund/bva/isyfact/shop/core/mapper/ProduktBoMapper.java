package de.bund.bva.isyfact.shop.core.mapper;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.persistence.entity.Produkt;

import java.util.ArrayList;
import java.util.List;

public class ProduktBoMapper {

    /**
     * Returns the corresponding Produkt business object for a given Produkt entity.
     * @param produkt entity
     * @return the corresponding business object
     */
    public static ProduktBo fromEntity(Produkt produkt) {

        return new ProduktBo(
                produkt.getId(),
                produkt.getName(),
                produkt.getBeschreibung()
        );
    }
    /**
     * Returns the corresponding Produkt entity for a given Produkt business object.
     * @param produktBo
     * @return the corresponding entity
     */
    public static Produkt toEntity(ProduktBo produktBo) {

        return new Produkt(
                produktBo.getId(),
                produktBo.getName(),
                produktBo.getBeschreibung()
        );
    }

    /**
     * Returns the corresponding list of Produkt business objects for a given list of Produkt entities.
     * @param produktList
     * @return the corresponding list of  business objects
     */
    public static List<ProduktBo> fromEntityList(List<Produkt> produktList) {
        List<ProduktBo> result = new ArrayList<>();
        for (Produkt produkt: produktList) {
            result.add(ProduktBoMapper.fromEntity(produkt));
        }
        return result;
    }

    /**
     * Returns the corresponding list of Produkt entities for a given list of Produkt business objects.
     * @param produktBoList
     * @return the corresponding list of entities
     */
    public static List<Produkt>  toEntityList(List<ProduktBo> produktBoList) {
        List<Produkt> result = new ArrayList<>();
        for (ProduktBo produktBo: produktBoList) {
            result.add(ProduktBoMapper.toEntity(produktBo));
        }
        return result;
    }

}
