package de.bund.bva.isyfact.shop.persistence.dao;

import java.util.List;

import de.bund.bva.isyfact.shop.persistence.entity.Produkt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduktRepository extends JpaRepository<Produkt, Long> {
    List<Produkt> findByName(String substring);
}
