package ru.foxcrazy.simplecooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.foxcrazy.simplecooker.domain.Ingredient;
import ru.foxcrazy.simplecooker.repository.IngredientRepo;

import java.util.*;

@RestController
@RequestMapping("api")
public class MainController {
    @Autowired
    private IngredientRepo ingredientRepo;
    public List<Map<String,String>> messages = new ArrayList<Map<String,String>>(){{
        add(new HashMap<String,String>() {{put("id","1");put("text","message1");}});
    }};
    @GetMapping
    public String mainPage() {
        return "index";
    }


    @GetMapping("msg")
    public List<Map<String, String>> ans(){
        return messages;
    }




}
