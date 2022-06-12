package de.kemary.warehouse.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class TagDTO {
    private Long id;
    private String name;
    private HttpStatus httpStatus;
}
