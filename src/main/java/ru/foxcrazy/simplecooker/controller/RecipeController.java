package ru.foxcrazy.simplecooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.foxcrazy.simplecooker.domain.Ingredient;
import ru.foxcrazy.simplecooker.domain.Recipe;
import ru.foxcrazy.simplecooker.domain.RecipeItems;
import ru.foxcrazy.simplecooker.repository.RecipeItemsRepo;
import ru.foxcrazy.simplecooker.repository.RecipeRepo;
import ru.foxcrazy.simplecooker.service.UserRepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.foxcrazy.simplecooker.token.JwtTokenUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/recipe")
@CrossOrigin
public class RecipeController {
    @Autowired
    private RecipeRepo recipeRepo;
    @Autowired
    private RecipeItemsRepo recipeItemsRepo;
    @Autowired
    private UserRepositoryService userRepositoryService;

    @GetMapping("{id}")
    public ResponseEntity<Recipe> ans(@PathVariable Integer id){
        Recipe rec = recipeRepo.findById(id).orElse(null);
        //System.out.println(rec);
        return new ResponseEntity <>(rec,HttpStatus.OK);
    }
    @GetMapping("name/{str}")
    public ResponseEntity<List<Recipe>> listRec(@PathVariable String str){
        System.out.println(str + str.toLowerCase());
        List<Recipe> listRec = recipeRepo.findAllByNameContainingIgnoreCase(str);
        return new ResponseEntity <>(listRec, HttpStatus.OK);
    }

    @GetMapping("main")
    public ResponseEntity<List<Recipe>> listMainRec(){
        List<Recipe> listRec = recipeRepo.findTop10By();
        return new ResponseEntity <>(listRec, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe, @RequestHeader("Authorization") String token){
        //System.out.println(token.substring(7));

        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        Integer usId = userRepositoryService.getUserByLogin(jwtTokenUtil.getUsernameFromToken(token.substring(7))).getUserId();
        System.out.println(usId);System.out.println(recipe);
        if (recipe.getName().isEmpty() || recipe.getDescription().isEmpty() || recipe.getActions().isEmpty() )
            return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
        Recipe _recipe = recipeRepo
                .save(new Recipe(recipe.getName(), recipe.getActions(), recipe.getDescription(), usId));
        return new ResponseEntity<>(_recipe, HttpStatus.CREATED);

    }
    @PutMapping("{recId}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Integer recId,@RequestBody Recipe recipe){
        try {
            Optional<Recipe>  recData = recipeRepo.findById(recId);
            if (recipe.getName().isEmpty() || recipe.getDescription().isEmpty() || recipe.getActions().isEmpty() )
                return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
            if (recData.isPresent()){
                Recipe _recipe= recData.get();
                _recipe.setName(recipe.getName());
                _recipe.setActions((recipe.getActions()));
                _recipe.setDescription((recipe.getDescription()));
                _recipe.setAuthorId(recipe.getAuthorId());
                return new ResponseEntity<>(recipeRepo.save(_recipe), HttpStatus.CREATED);
            }

            return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("delete/{recId}")
    public ResponseEntity<Integer> deleteRecipe(@PathVariable Integer recId){
        try{
            recipeItemsRepo.removeAllByRecipeId(recId);
            System.out.println("removed recipe links "+recId);
            recipeRepo.deleteById(recId);
            System.out.println("removed recipe "+recId);
            return new ResponseEntity<>(recId,HttpStatus.OK);
        }
        catch (Exception e){e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);}
    }
}
