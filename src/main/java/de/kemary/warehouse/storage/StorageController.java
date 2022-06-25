package de.kemary.warehouse.storage;

import de.kemary.warehouse.item.Item;
import de.kemary.warehouse.item.ItemRepository;
import de.kemary.warehouse.location.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/storage")
@AllArgsConstructor
public class StorageController {
    final StorageRepository storageRepository;
    final ItemRepository itemRepository;
    final LocationRepository locationRepository;

    @GetMapping
    public List<Storage> getStorage(){
        return storageRepository.findAll();
    }

    @PostMapping
    public void postStorage(@RequestBody StorageDTO storageDTO){
        Optional<Item> item;
        if(storageDTO.getItemName() != null) {
            item = itemRepository.findAllByName(storageDTO.getItemName());
        } else {
            item = itemRepository.findByBarcode(storageDTO.getBarcode());
        }
        if(item.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Item does not exist");
        }
        if(locationRepository.findByName(storageDTO.getLocation()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Location is not known");
        }
        var storageInv = storageRepository.findByItem(item.get());
        if(storageInv.isPresent()){
            if(Objects.equals(storageInv.get().getLocation().getName(), storageDTO.getLocation())){
                storageInv.get().setCount(storageInv.get().getCount() + storageDTO.getCount());
                storageRepository.save(storageInv.get());
                return;
            }
            var storage = new Storage(item.get(), storageDTO.getBoughtAt(),storageDTO.getExpiresOn(), storageDTO.getCount(), locationRepository.findByName(storageDTO.getLocation()).get());
            storageRepository.save(storage);
            return;
        }
        var storage = new Storage(item.get(), storageDTO.getBoughtAt(),storageDTO.getExpiresOn(), storageDTO.getCount(), locationRepository.findByName(storageDTO.getLocation()).get());
        storageRepository.save(storage);
    }
}
