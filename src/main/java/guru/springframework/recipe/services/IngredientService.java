package guru.springframework.recipe.services;

import guru.springframework.recipe.domain.Ingredient;

public interface IngredientService {
    Ingredient findById(Long id);
}
