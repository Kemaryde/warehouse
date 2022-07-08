package de.kemary.warehouse.storage;

import de.kemary.warehouse.item.Item;
import de.kemary.warehouse.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> findByItem(Item item);
    Optional<Storage> findByItemId(Long itemId);
    List<Storage> findAllByItem(Item item);
    List<Storage> findAllByLocation(Location location);
}