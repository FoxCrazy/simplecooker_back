package ru.foxcrazy.simplecooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.foxcrazy.simplecooker.domain.Ingredient;
import ru.foxcrazy.simplecooker.domain.Recipe;
import ru.foxcrazy.simplecooker.repository.IngredientRepo;

import java.util.List;

@RestController
@RequestMapping("api/ingredient")
@CrossOrigin
public class IngredientController {
    @Autowired
    private IngredientRepo ingredientRepo;

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
        System.out.println(str + str.toLowerCase());
        List<Ingredient> listIng = ingredientRepo.findAllByIngredientNameContaining(str);
        return new ResponseEntity<>(listIng, HttpStatus.OK);
    }

}
