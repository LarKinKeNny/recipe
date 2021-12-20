package guru.springframework.recipe.services;

import guru.springframework.recipe.converters.RecipeCommandToRecipe;
import guru.springframework.recipe.converters.RecipeToRecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    private final Long id = 1L;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getRecipes() {
        when(recipeRepository.findAll()).thenReturn(Set.of(new Recipe()));
        var recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(recipeRepository.findById(id)).thenReturn(Optional.of(new Recipe()));
        assertNotNull(recipeService.findById(id));
        verify(recipeRepository).findById(eq(id));
    }

    @Test
    public void getRecipesTest() throws Exception {

        Recipe recipe = new Recipe();
        var data = new ArrayList<Recipe>();
        data.add(recipe);

        when(recipeService.getRecipes()).thenReturn(data);

        List<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    void deleteById() {
        recipeService.deleteById(id);
        verify(recipeRepository).deleteById(eq(id));
    }
}