package de.kemary.warehouse.item;

import de.kemary.warehouse.Type;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

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

    public Item(String name, Type type, String fracturedBy, Long barcode) {
        this.fracturedBy = fracturedBy;
        this.name = name;
        this.type = type;
        this.barcode = barcode;
    }
}
