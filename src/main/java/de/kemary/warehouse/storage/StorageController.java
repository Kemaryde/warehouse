package de.kemary.warehouse.storage;

import de.kemary.warehouse.item.Item;
import de.kemary.warehouse.item.ItemRepository;
import de.kemary.warehouse.location.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    @GetMapping(path = "/items")
    public List<Storage> getStorage(@RequestParam(required = false)Long itemID,
                                    @RequestParam(required = false)Long locationID){
        if(itemID != null){
            var item = itemRepository.findById(itemID);
            if(item.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
            var storage = storageRepository.findAllByItem(item.get());
            if(storage.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, String.format("storage for item %s is empty", item.get().getName()));
            }
            return storage;
        }else if(locationID != null) {
            var location = locationRepository.findById(locationID);
            if(location.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Location is not known");
            }
            return storageRepository.findAllByLocation(location.get());
        }
        return storageRepository.findAll();
    }
    @GetMapping(path = "/item-count")
    public Integer getStorageItemCount(@RequestParam Long itemID){
        var item = itemRepository.findById(itemID);
        if(item.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        var storage = storageRepository.findAllByItem(item.get());
        if(storage.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, String.format("storage for item %s is empty", item.get().getName()));
        }
        int i = 0;
        Integer count = 0;
        while (storage.size() != i){
            count += storage.get(i).getCount();
            i++;
        }
        return count;
    }

    @PostMapping
    public void postStorage(@RequestBody StorageDTO storageDTO){
        Optional<Item> item;
        if(storageDTO.getItemName() != null) {
            item = itemRepository.findAllByName(storageDTO.getItemName());
        } else if(storageDTO.getItemID() != null) {
            item = itemRepository.findById(storageDTO.getItemID());
        } else {
            item = itemRepository.findByBarcode(storageDTO.getBarcode());
        }
        if(item.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Item does not exist");
        }
        if(locationRepository.findByName(storageDTO.getLocation()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Location is not known");
        }
        var storageInv = storageRepository.findByItemId(item.get().getId());
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

    @GetMapping(path = "/locations")
    public List<Long> getAllStorages(@RequestParam(required = false)Long itemID){
        List<Storage> storageList;
        if(itemID != null){
            var item = itemRepository.findById(itemID);
            if(item.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
            var storage = storageRepository.findAllByItem(item.get());
            if(storage.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, String.format("storage for item %s is empty", item.get().getName()));
            }
            return storage.stream().map(storage1 -> storage1.getLocation().getId()).toList();
        }else {
            storageList = storageRepository.findAll();
            
        }
        if(storageList.size() <= 0){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return storageList.stream().map(storage -> storage.getLocation().getId()).distinct().toList();
    }
}
