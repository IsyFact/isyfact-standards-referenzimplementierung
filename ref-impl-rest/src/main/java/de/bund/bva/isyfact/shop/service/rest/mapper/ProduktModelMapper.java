package de.bund.bva.isyfact.shop.service.rest.mapper;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.model.ProduktModel;

import java.util.List;
import java.util.stream.Collectors;

public class ProduktModelMapper {

    public static ProduktModel fromBo(ProduktBo produktBo) {

        return new ProduktModel(
                produktBo.getId(),
                produktBo.getName(),
                produktBo.getBeschreibung()
        );
    }
    public static ProduktBo toBo(ProduktModel produktModel) {

        return new ProduktBo(
                produktModel.getId(),
                produktModel.getName(),
                produktModel.getBeschreibung()
        );
    }

    public static List<ProduktModel> fromBoList(List<ProduktBo> produktBoList) {
        return produktBoList.stream().map(ProduktModelMapper::fromBo).collect(Collectors.toList());
    }

    public static List<ProduktBo>  toBoList(List<ProduktModel> produktModelList) {
        return produktModelList.stream().map(ProduktModelMapper::toBo).collect(Collectors.toList());
    }
}
