package ru.foxcrazy.simplecooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.foxcrazy.simplecooker.domain.RecipeItems;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RecipeItemsRepo extends JpaRepository<RecipeItems,Integer> {
    List<RecipeItems> findAllByRecipeId(Integer recipeId);
    @Modifying
    @Transactional
    void removeAllByIngredientId(Integer ingredientId);
    @Modifying
    @Transactional
    void removeAllByRecipeId(Integer recipeId);
}
