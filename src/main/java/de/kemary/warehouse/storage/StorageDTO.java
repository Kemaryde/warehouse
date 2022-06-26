package de.kemary.warehouse.storage;

import de.kemary.warehouse.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter @Getter
public class StorageDTO {
    private Long id;
    private Item item;
    private String itemName;
    private Long itemID;
    private Long barcode;
    private String location;
    private LocalDate boughtAt;
    private LocalDate expiresOn;
    private Integer count;
    private String message;

}
