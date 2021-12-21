package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findById(Long id);
    IngredientCommand saveIngredient(IngredientCommand ingredientCommand);
}
