package ru.foxcrazy.simplecooker.domain.model;

public class frontendIngredient {
    private Integer id;
    private Integer calories;
    private String ingredientName;
    private Integer grammar;

    public frontendIngredient(Integer id, Integer calories, String ingredientName, Integer grammar) {
        this.id = id;
        this.calories = calories;
        this.ingredientName = ingredientName;
        this.grammar = grammar;
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

    public Integer getGrammar() {
        return grammar;
    }

    public void setGrammar(Integer grammar) {
        this.grammar = grammar;
    }

    @Override
    public String toString() {
        return (id +" "+ calories+" "+ ingredientName+" "+ grammar);
    }
}
