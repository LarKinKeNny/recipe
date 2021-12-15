package guru.springframework.recipe.domain;

import javax.persistence.Entity;

@Entity
public class UnitOfMeasure extends BaseEntity {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
