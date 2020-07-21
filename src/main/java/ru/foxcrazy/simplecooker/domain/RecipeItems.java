package ru.foxcrazy.simplecooker.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recipe_items")
public class RecipeItems implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer linkId;

    @Column(name = "ingredient_id", nullable = false)
    private Integer ingredientId;

    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId;

    @Column
    private Integer grammar;

    public Integer getLinkId() {
        return linkId;
    }

    public RecipeItems() {
    }

    public RecipeItems(Integer ingredientId, Integer recipeId, Integer grammar) {
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
        this.grammar = grammar;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getGrammar() {
        return grammar;
    }

    public void setGrammar(Integer grammar) {
        this.grammar = grammar;
    }

    @Override
    public String toString() {
        return "Recipe item [ing id= " + ingredientId + ", rec id=" + recipeId + ", grammar=" + grammar + "]";
    }
}