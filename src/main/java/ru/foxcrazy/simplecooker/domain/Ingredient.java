package ru.foxcrazy.simplecooker.domain;

import javax.persistence.*;

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private Integer calories;

    @Column(name = "ingredient_name")
    private String ingredientName;

    @Override
    public String toString() {
        return "Employee [id= " + id + ", name=" + ingredientName + ", calories=" + calories + "]";
    }
}