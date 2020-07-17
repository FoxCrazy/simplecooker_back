package ru.foxcrazy.simplecooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.foxcrazy.simplecooker.domain.Ingredient;
import ru.foxcrazy.simplecooker.domain.RecipeItems;
import ru.foxcrazy.simplecooker.domain.model.frontendIngredient;
import ru.foxcrazy.simplecooker.repository.IngredientRepo;
import ru.foxcrazy.simplecooker.repository.RecipeItemsRepo;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/items")
@CrossOrigin
public class RecipeItemsController {
    @Autowired
    private RecipeItemsRepo recipeItemsRepo;
    @Autowired
    private  IngredientRepo ingredientRepo;

    @GetMapping("forrecipe/{recipeId}")
    public ResponseEntity<List<frontendIngredient>> listOfIng(@PathVariable Integer recipeId){
        List<RecipeItems> listItems = recipeItemsRepo.findAllByRecipeId(recipeId);

        List<frontendIngredient> listToFront=new ArrayList<frontendIngredient>();

        for (RecipeItems recItem : listItems){
            Ingredient ing=ingredientRepo.findById(recItem.getIngredientId()).orElse(null);
            //frontendIngredient fing=new frontendIngredient(ing.getId(), ing.getCalories(), ing.getIngredientName(), recItem.getGrammar());
            listToFront.add(new frontendIngredient(ing.getId(), ing.getCalories(), ing.getIngredientName(), recItem.getGrammar()));
            //System.out.println(fing.toString());
        }

    return new ResponseEntity<>(listToFront, HttpStatus.OK);
    }
}
