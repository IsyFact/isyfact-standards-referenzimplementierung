package de.bund.bva.isyfact.shop.persistence.mapper;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.persistence.entity.ProduktEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProduktEntityMapper {

    public static ProduktBo toBo(ProduktEntity produkt) {

        return new ProduktBo(
                produkt.getId(),
                produkt.getName(),
                produkt.getBeschreibung()
        );
    }
    public static ProduktEntity fromBo(ProduktBo produktBo) {

        return new ProduktEntity(
                produktBo.getId(),
                produktBo.getName(),
                produktBo.getBeschreibung()
        );
    }

    public static List<ProduktBo> toBoList(List<ProduktEntity> produktEntityList) {
        return produktEntityList.stream().map(ProduktEntityMapper::toBo).collect(Collectors.toList());
    }

    public static List<ProduktEntity> fromBoList(List<ProduktBo> produktBoList) {
        return produktBoList.stream().map(ProduktEntityMapper::fromBo).collect(Collectors.toList());
    }
}
