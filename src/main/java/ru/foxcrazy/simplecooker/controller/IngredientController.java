package ru.foxcrazy.simplecooker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.foxcrazy.simplecooker.domain.Ingredient;
import ru.foxcrazy.simplecooker.domain.Recipe;
import ru.foxcrazy.simplecooker.repository.IngredientRepo;
import ru.foxcrazy.simplecooker.repository.RecipeItemsRepo;

import java.util.List;

@RestController
@RequestMapping("api/ingredient")
@CrossOrigin
public class IngredientController {
    private final IngredientRepo ingredientRepo;
    private final RecipeItemsRepo recipeItemsRepo;

    public IngredientController(IngredientRepo ingredientRepo,RecipeItemsRepo recipeItemsRepo){
        this.ingredientRepo=ingredientRepo;
        this.recipeItemsRepo=recipeItemsRepo;
    }

    @GetMapping("{id}") //todo: parameter instead of url
    public ResponseEntity<Ingredient> ans(@PathVariable Integer id){
        Ingredient ing = ingredientRepo.findById(id).orElse(null);
        //Optional<Ingredient> ing = ingredientRepo.findById(id);
        //System.out.println(id);
        //System.out.println(ing.toString());
        return new ResponseEntity<>(ing, HttpStatus.OK);
    }
    @GetMapping("name/{str}") //todo: parameter instead of url
    public ResponseEntity<List<Ingredient>> listOfIng(@PathVariable String str){
        //System.out.println(str + str.toLowerCase());
        List<Ingredient> listIng = ingredientRepo.findAllByIngredientNameContainingIgnoreCase(str);
        return new ResponseEntity<>(listIng, HttpStatus.OK);
    }

    @GetMapping("main")
    public ResponseEntity<List<Ingredient>> listMainIng(){
        List<Ingredient> listIng = ingredientRepo.findTop10By();
        return new ResponseEntity <>(listIng, HttpStatus.OK);
    }


    @PostMapping("create")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient){
        //System.out.println(ingredient);
        if (ingredient.getIngredientName().isEmpty() || ingredient.getCalories()==0 )
            return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
        Ingredient _ingredient = ingredientRepo
                .save(new Ingredient(ingredient.getCalories(), ingredient.getIngredientName()));
        return new ResponseEntity<>(_ingredient, HttpStatus.CREATED);
    }
    @DeleteMapping("delete/{ingId}")
    public ResponseEntity<Integer> deleteIngredient(@PathVariable Integer ingId){
        try{
            recipeItemsRepo.removeAllByIngredientId(ingId);
            System.out.println("removed ingredient links "+ingId);
            ingredientRepo.deleteById(ingId);
            System.out.println("removed ingredient "+ingId);
            return new ResponseEntity<>(ingId,HttpStatus.OK);
        }
        catch (Exception e){return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);}
    }


}
