package ru.foxcrazy.simplecooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.foxcrazy.simplecooker.domain.Recipe;

import java.util.List;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe,Integer> {
    List<Recipe> findAllByNameContainingIgnoreCase(String recipeName);
    List<Recipe> findTop10By();

}
