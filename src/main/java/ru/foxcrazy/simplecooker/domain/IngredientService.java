package ru.foxcrazy.simplecooker.domain;

import java.util.List;
import ru.foxcrazy.simplecooker.domain.Ingredient;
public interface IngredientService {
    List<Ingredient> get();

    Ingredient get(int id);

    void save(Ingredient ingredient);

    void delete(int id);
}
