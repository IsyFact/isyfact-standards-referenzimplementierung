package de.bund.bva.isyfact.shop.core.adapter;

import de.bund.bva.isyfact.shop.core.adapter.mapper.ProduktClientModelMapper;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.api.ProdukteClientApi;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import de.bund.bva.isyfact.shop.service.rest.model.ProduktClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProdukteAdapter extends ProdukteClientApi {

    @Autowired
    public ProdukteAdapter() {
        super();

        // Set URI to Produkte-Service
        // ToDo: Read REST basePath to corresponding Service from configuration
        String basePath = "http://localhost:8081";
        this.getApiClient().setBasePath(basePath);
    }


    /**
     * Searches for products by a given name.
     * If no such name is passed, all products are returned, without any restriction.
     *
     * @param name of products to search for
     * @return list of ProduktBo found
     * @throws ProduktNotFoundException if no such produkt exists
     */
    public List<ProduktBo> findAllProduktBoByName(String name) throws ProduktNotFoundException {

        // 1. Validation of input parameters

        // 2. Mapping of input business objects to generated rest.model

        // 3. Call of REST endpoint
        List<ProduktClientModel> produktClientModelList;
        try {
            Flux<ProduktClientModel> response = this.findAllProdukteByName(name, 10);
            produktClientModelList = response.collectList().block();
        } catch (WebClientResponseException wcre) {
            // 4. Error handling
            if (wcre.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                throw new ProduktNotFoundException();
            }
            throw new RuntimeException(wcre);
        }

        // 5. Validation of REST response
        if (produktClientModelList == null || produktClientModelList.isEmpty()) {
            throw new ProduktNotFoundException();
        }

        // 6. Mapping of REST response to output business object

        return ProduktClientModelMapper.toBoList(produktClientModelList);
    }

    /**
     * Searches for the product with a given id.
     *
     * @param produktId under which the product is stored in the database
     * @return the ProduktBo with the given id
     * @throws ProduktNotFoundException if no such produkt exists
     */
    public ProduktBo getProduktBoById(Long produktId) throws ProduktNotFoundException {

        // 1. Validation of input parameters
        if (produktId == null) {
            return null;
        }
        // 2. Mapping of input business objects to generated rest.model

        // 3. Call of REST endpoint
        ProduktClientModel produktClientModel;
        try {
            Mono<ProduktClientModel> response = this.getProduktById(produktId);
            produktClientModel = response.block();
        } catch (WebClientResponseException wcre) {
            // 4. Error handling
            if (wcre.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new ProduktNotFoundException();
            }
            throw new RuntimeException(wcre);
        }

        // 5. Validation of REST response
        if (produktClientModel == null) {
            throw new ProduktNotFoundException();
        }

        // 6. Mapping of REST response to output business object

        return ProduktClientModelMapper.toBo(produktClientModel);
    }

}
