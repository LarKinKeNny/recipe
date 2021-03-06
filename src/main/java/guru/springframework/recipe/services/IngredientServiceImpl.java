package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.converters.IngredientCommandToIngredient;
import guru.springframework.recipe.converters.IngredientToIngredientCommand;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.IngredientRepository;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientCommand ingredientConverter;
    private final IngredientCommandToIngredient ingredientCommandConverter;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientConverter,
                                 IngredientCommandToIngredient ingredientCommandConverter, RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientConverter = ingredientConverter;
        this.ingredientCommandConverter = ingredientCommandConverter;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findById(Long id) {
        return ingredientConverter.convert(ingredientRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredient(IngredientCommand ingredientCommand) {
        var recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (recipeOptional.isEmpty())
            return new IngredientCommand();

        Recipe recipe = recipeOptional.get();
        recipe.getIngredients().stream()
                .filter(x -> x.getId().equals(ingredientCommand.getId())).findAny()
                .ifPresentOrElse(x -> {
                            x.setDescription(ingredientCommand.getDescription());
                            x.setAmount(ingredientCommand.getAmount());
                            x.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId()).orElseThrow());
                        },
                        () -> {
                            var ingredient = ingredientCommandConverter.convert(ingredientCommand);
                            ingredient.setRecipe(recipe);
                            recipe.addIngredient(ingredient);
                        });
        Recipe savedRecipe = recipeRepository.save(recipe);

        return savedRecipe.getIngredients().stream()
                .filter(x -> x.getId().equals(ingredientCommand.getId()) ||
                             (x.getDescription().equals(ingredientCommand.getDescription()) &&
                              x.getAmount().equals(ingredientCommand.getAmount()) &&
                              x.getUnitOfMeasure().getId().equals(ingredientCommand.getUom().getId())))
                .map(ingredientConverter::convert).findAny().orElseThrow();
    }

    @Override
    @Transactional
    public void deleteIngredient(Long id) {
        var ingredientOptional = ingredientRepository.findById(id);
        if (ingredientOptional.isPresent()) {
            var ingredient = ingredientOptional.get();
            var recipeOptional = recipeRepository.findById(ingredient.getRecipe().getId());
            recipeOptional.ifPresent(recipe -> {
                recipe.getIngredients().remove(ingredient);
                recipeRepository.save(recipe);
            });
            ingredientRepository.deleteById(id);
        }
    }
}
