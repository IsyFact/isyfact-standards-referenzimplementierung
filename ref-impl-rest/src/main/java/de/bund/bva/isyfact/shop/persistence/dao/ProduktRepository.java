package de.bund.bva.isyfact.shop.persistence.dao;

import java.util.List;

import de.bund.bva.isyfact.shop.persistence.entity.ProduktEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduktRepository extends JpaRepository<ProduktEntity, Long> {
    List<ProduktEntity> findByName(String substring);
}
