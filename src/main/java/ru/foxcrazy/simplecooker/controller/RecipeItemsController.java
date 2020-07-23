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
        try{
            List<RecipeItems> listItems = recipeItemsRepo.findAllByRecipeId(recipeId);

            List<frontendIngredient> listToFront = new ArrayList<frontendIngredient>();

            for (RecipeItems recItem : listItems) {
                Ingredient ing = ingredientRepo.findById(recItem.getIngredientId()).orElse(null);
                //frontendIngredient fing=new frontendIngredient(ing.getId(), ing.getCalories(), ing.getIngredientName(), recItem.getGrammar());
                listToFront.add(new frontendIngredient(ing.getId(), ing.getCalories(), ing.getIngredientName(), recItem.getGrammar()));
                //System.out.println(fing.toString());
            }

            return new ResponseEntity<>(listToFront, HttpStatus.OK);
        }catch (Exception e){return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);}
    }
    @PostMapping("setingredients/{recipeId}")
    public ResponseEntity<List<frontendIngredient>> createIngList(@PathVariable Integer recipeId, @RequestBody List<frontendIngredient> ingredientList){
        try{
        List<RecipeItems> listToSave=new ArrayList<RecipeItems>();
            System.out.println(ingredientList);
        for (frontendIngredient ingItem : ingredientList){
            if (ingItem.getId()==0)
                return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
            if(ingItem.getGrammar()==0)
                ingItem.setGrammar(100);
            listToSave.add(new RecipeItems(ingItem.getId(),recipeId,ingItem.getGrammar()));

        }
        System.out.println(listToSave);
        recipeItemsRepo.saveAll(listToSave);
        return new ResponseEntity<>(null, HttpStatus.OK);}
        catch (Exception e){return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);}
    }

    @PutMapping("setingredients/{recipeId}")
    public ResponseEntity<List<frontendIngredient>> updateIngList(@PathVariable Integer recipeId, @RequestBody List<frontendIngredient> ingredientList){
        try{
            recipeItemsRepo.removeAllByRecipeId(recipeId);

            List<RecipeItems> listToSave=new ArrayList<RecipeItems>();
            System.out.println(ingredientList);
            for (frontendIngredient ingItem : ingredientList){
                if (ingItem.getId()==0)
                    return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
                if(ingItem.getGrammar()==0)
                    ingItem.setGrammar(100);
                listToSave.add(new RecipeItems(ingItem.getId(),recipeId,ingItem.getGrammar()));

            }
            System.out.println(listToSave);
            recipeItemsRepo.saveAll(listToSave);
            return new ResponseEntity<>(null, HttpStatus.OK);}
        catch (Exception e){return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);}
    }
}
