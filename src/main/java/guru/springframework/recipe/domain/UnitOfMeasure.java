package guru.springframework.recipe.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;


@Getter
@Setter
@Entity
public class UnitOfMeasure extends BaseEntity {
    private String description;
}
