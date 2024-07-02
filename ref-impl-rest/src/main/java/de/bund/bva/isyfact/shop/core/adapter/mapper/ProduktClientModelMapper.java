package de.bund.bva.isyfact.shop.core.adapter.mapper;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.model.ProduktClientModel;

import java.util.List;
import java.util.stream.Collectors;

public class ProduktClientModelMapper {

    public static ProduktClientModel fromBo(ProduktBo produktBo) {

        return new ProduktClientModel()
                .id(produktBo.getId())
                .name(produktBo.getName())
                .beschreibung(produktBo.getBeschreibung());
   }
    public static ProduktBo toBo(ProduktClientModel produktClientModel) {

        return new ProduktBo(
                produktClientModel.getId(),
                produktClientModel.getName(),
                produktClientModel.getBeschreibung()
        );
    }

    public static List<ProduktClientModel> fromBoList(List<ProduktBo> produktBoList) {
        return produktBoList.stream().map(ProduktClientModelMapper::fromBo).collect(Collectors.toList());
    }

    public static List<ProduktBo> toBoList(List<ProduktClientModel> produktClientModelList) {
        return produktClientModelList.stream().map(ProduktClientModelMapper::toBo).collect(Collectors.toList());
    }
}
