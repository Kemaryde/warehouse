package de.kemary.warehouse.storage;

import de.kemary.warehouse.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;
import java.time.LocalDate;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Storage {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Item item;
    private LocalDate boughtAt;
    private LocalDate expiresOn;
    private Integer count;

    public Storage(Item item, LocalDate boughtAt, LocalDate expiresOn, Integer count) {
        this.item = item;
        this.boughtAt = boughtAt;
        this.expiresOn = expiresOn;
        this.count = count;
    }
}
