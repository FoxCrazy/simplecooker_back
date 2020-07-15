package ru.foxcrazy.simplecooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.foxcrazy.simplecooker.domain.Ingredient;
import ru.foxcrazy.simplecooker.repository.IngredientRepo;

@RestController
@RequestMapping("api/ingredient")
public class IngredientController {
    @Autowired
    private IngredientRepo ingredientRepo;

    @GetMapping("{id}")
    public String ans(@PathVariable Integer id){
        Ingredient ing = ingredientRepo.findById(id).orElse(null);
        //Optional<Ingredient> ing = ingredientRepo.findById(id);
        //System.out.println(id);
        //System.out.println(ing.toString());
        return ing.toString();
    }
}
