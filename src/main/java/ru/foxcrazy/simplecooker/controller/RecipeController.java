package ru.foxcrazy.simplecooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.foxcrazy.simplecooker.domain.Ingredient;
import ru.foxcrazy.simplecooker.domain.Recipe;
import ru.foxcrazy.simplecooker.repository.RecipeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("api/recipe")
@CrossOrigin
public class RecipeController {
    @Autowired
    private RecipeRepo recipeRepo;

    @GetMapping("{id}") //todo: parameter instead of url
    public ResponseEntity<Recipe> ans(@PathVariable Integer id){
        Recipe rec = recipeRepo.findById(id).orElse(null);
        //System.out.println(rec);
        return new ResponseEntity <>(rec,HttpStatus.OK);
    }
    @GetMapping("name/{str}") //todo: parameter instead of url
    public ResponseEntity<List<Recipe>> listRec(@PathVariable String str){
        System.out.println(str + str.toLowerCase());
        List<Recipe> listRec = recipeRepo.findAllByNameContainingIgnoreCase(str);
        return new ResponseEntity <>(listRec, HttpStatus.OK);
    }
}
