package de.kemary.warehouse.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByNameAndFracturedBy (String name, String fracturedBy);
    Optional<Item> findAllByName (String name);
    Optional<Item> findByBarcode (Long barcode);
}
