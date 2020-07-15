package ru.foxcrazy.simplecooker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "recipe_items")
public class RecipeItems implements Serializable {

    @Id
    @Column(name = "ingredient_id", nullable = false)
    private Integer ingredientId;
    @Id
    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId;

    @Column
    private Integer grammar;

    @Override
    public String toString() {
        return "Employee [ing id= " + ingredientId + ", rec id=" + recipeId + ", grammar=" + grammar + "]";
    }
}