package de.kemary.warehouse.item;

import de.kemary.warehouse.Type;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;

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
    @Column(columnDefinition = "TEXT")
    private String description;
    private Long barcode;
    private String imageURL;
    private String tagString;

    public Item(String name, Type type, String fracturedBy, Long barcode, String imageURL, String tagString, String description) {
        this.fracturedBy = fracturedBy;
        this.name = name;
        this.type = type;
        this.barcode = barcode;
        this.imageURL = imageURL;
        this.tagString = tagString;
        this.description = description;
    }
}
