package de.bund.bva.isyfact.shop.core.mapper;

import de.bund.bva.isyfact.shop.persistence.entity.Produkt;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;

import java.util.ArrayList;
import java.util.List;

public class ProduktBoMapper {

    public static ProduktBo fromEntity(Produkt produkt) {

        return new ProduktBo(
                produkt.getId(),
                produkt.getName(),
                produkt.getBeschreibung()
        );
    }
    public static Produkt toEntity(ProduktBo produktBo) {

        return new Produkt(
                produktBo.getId(),
                produktBo.getName(),
                produktBo.getBeschreibung()
        );
    }

    public static List<ProduktBo> fromEntityList(List<Produkt> produkte) {
        List<ProduktBo> result = new ArrayList<>();
        for (Produkt produkt: produkte) {
            result.add(ProduktBoMapper.fromEntity(produkt));
        }
        return result;
    }

    public static List<Produkt>  toEntityList(List<ProduktBo> produktBoList) {
        List<Produkt> result = new ArrayList<>();
        for (ProduktBo produktBo: produktBoList) {
            result.add(ProduktBoMapper.toEntity(produktBo));
        }
        return result;
    }
}
