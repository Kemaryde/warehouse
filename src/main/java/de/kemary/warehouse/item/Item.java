package de.kemary.warehouse.item;

import de.kemary.warehouse.Type;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fracturedBy;
    private String name;
    private Type type;
    private Long barcode;
    private String imageURL;

    public Item(String name, Type type, String fracturedBy, Long barcode, String imageURL) {
        this.fracturedBy = fracturedBy;
        this.name = name;
        this.type = type;
        this.barcode = barcode;
        this.imageURL = imageURL;
    }
}
