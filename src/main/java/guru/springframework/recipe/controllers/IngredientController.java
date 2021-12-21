package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.services.IngredientService;
import guru.springframework.recipe.services.RecipeService;
import guru.springframework.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @RequestMapping("/recipe/{id}/ingredients")
    public String getIngredients(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable Long ingredientId, Model model) {
        model.addAttribute("ingredient",  ingredientService.findById(ingredientId));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable Long ingredientId, Model model) {
        model.addAttribute("ingredient",  ingredientService.findById(ingredientId));
        model.addAttribute("uomList", unitOfMeasureService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{id}/ingredient/new")
    public String addIngredient(@PathVariable Long id, Model model) {
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(id);
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable Long ingredientId, @PathVariable Long recipeId) {
        ingredientService.deleteIngredient(ingredientId);
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        var savedIngredient = ingredientService.saveIngredient(ingredientCommand);
        return "redirect:/recipe/ingredient/"+ savedIngredient.getId() + "/show";
    }
}
