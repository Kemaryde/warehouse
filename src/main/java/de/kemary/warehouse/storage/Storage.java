package de.kemary.warehouse.storage;

import de.kemary.warehouse.item.Item;
import de.kemary.warehouse.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    @ManyToOne
    private Location location;

    public Storage(Item item, LocalDate boughtAt, LocalDate expiresOn, Integer count, Location location) {
        this.item = item;
        this.boughtAt = boughtAt;
        this.expiresOn = expiresOn;
        this.count = count;
        this.location = location;
    }
}
