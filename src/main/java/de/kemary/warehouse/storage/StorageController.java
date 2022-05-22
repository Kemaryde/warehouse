package de.kemary.warehouse.storage;

import de.kemary.warehouse.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/storage")
@AllArgsConstructor
public class StorageController {
    final StorageRepository storageRepository;
    final ItemRepository itemRepository;

    @GetMapping
    public List<Storage> getStorage(){
        return storageRepository.findAll();
    }

    @PostMapping
    public void postStorage(@RequestBody StorageDTO storageDTO){
        var item = itemRepository.findAllByName(storageDTO.getItemName());
        if(item.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Item does not exist");
        }
        var storageInv = storageRepository.findByItem(item.get());
        if(storageInv.isPresent()){
            storageInv.get().setCount(storageInv.get().getCount() + storageDTO.getCount());
            storageRepository.save(storageInv.get());
            return;
        }
        var storage = new Storage(item.get(), storageDTO.getBoughtAt(),storageDTO.getExpiresOn(), storageDTO.getCount());
        storageRepository.save(storage);
    }
}
