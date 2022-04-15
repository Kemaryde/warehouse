package de.kemary.warehouse.item;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class ItemController {
    final ItemRepository itemRepository;

    @GetMapping
    public List<Item> getItems(){
        return itemRepository.findAll();
    }

    @PostMapping
    public void postItem(@RequestBody Item item){
        if(itemRepository.findItemByNameAndFracturedBy(item.getName(), item.getFracturedBy()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        var newItem = new Item(item.getName(), item.getType(), item.getFracturedBy(), item.getBoughtAt(), item.getExpiresOn(), item.getBarcode());
        itemRepository.save(newItem);
    }
}
