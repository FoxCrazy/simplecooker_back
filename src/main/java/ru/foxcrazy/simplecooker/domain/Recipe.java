package ru.foxcrazy.simplecooker.domain;

import javax.persistence.*;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId;

    @Column
    private String name;

    @Column(name = "author_id")
    private Integer authorId;

    @Column
    private String actions;

    @Column
    private String description;

    @Column(name = "created_at")
    private java.util.Date createdAt;

    @Override
    public String toString() {
        return "Employee [recipeID= " + recipeId + ", name=" + name + ", created at=" + createdAt + ", desc= "+ description + "]";
    }
}