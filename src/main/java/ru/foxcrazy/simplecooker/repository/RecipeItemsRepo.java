package ru.foxcrazy.simplecooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.foxcrazy.simplecooker.domain.RecipeItems;

import java.util.List;

@Repository
public interface RecipeItemsRepo extends JpaRepository<RecipeItems,Integer> {
    List<RecipeItems> findAllByRecipeId(Integer recipeId);
}
