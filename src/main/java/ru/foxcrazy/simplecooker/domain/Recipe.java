package ru.foxcrazy.simplecooker.domain;

import javax.persistence.*;
import java.util.Date;

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

    public Integer getRecipeId(){
        return recipeId;
    }

    public Recipe() {
    }

    public Recipe(String name, String actions, String description, Integer authorId) {
        this.name = name;
        this.actions = actions;
        this.description = description;
        this.createdAt = new Date();
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Recipe [recipeID= " + recipeId + ", name=" + name + ", created at=" + createdAt + ", desc= "+ description + "]";
    }
}