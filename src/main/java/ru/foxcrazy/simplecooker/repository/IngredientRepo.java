package ru.foxcrazy.simplecooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.foxcrazy.simplecooker.domain.Ingredient;

import java.util.List;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient,Integer> {
    List<Ingredient> findTop10By();
    List<Ingredient> findAllByIngredientNameContainingIgnoreCase(String ingredientName);

}
