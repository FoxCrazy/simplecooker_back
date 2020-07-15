package ru.foxcrazy.simplecooker.domain;

import javax.persistence.*;

@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private Integer calories;

    @Column(name = "ingredient_name",nullable = false)
    private String ingredientName;

    public Ingredient() {
    }

    public Ingredient(Integer calories, String ingredientName) {
        this.calories = calories;
        this.ingredientName = ingredientName;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String toString() {
        return "Ingredient [id= " + id + ", name=" + ingredientName + ", calories=" + calories + "]";
    }
}