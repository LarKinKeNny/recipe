package guru.springframework.recipe.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Notes extends BaseEntity {
    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;
}
