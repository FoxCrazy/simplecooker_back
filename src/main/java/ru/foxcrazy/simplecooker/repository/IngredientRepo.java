package ru.foxcrazy.simplecooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.foxcrazy.simplecooker.domain.Ingredient;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient,Integer> {


}
