package guru.springframework.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true, exclude = {"recipe"})
@Data
@Entity
public class Notes extends BaseEntity {
    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;
}
