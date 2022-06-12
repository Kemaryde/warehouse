package de.kemary.warehouse.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Table
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
