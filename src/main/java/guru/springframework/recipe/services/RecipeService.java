package guru.springframework.recipe.services;

import guru.springframework.recipe.domain.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipes();
    Recipe findById(Long id);
}
