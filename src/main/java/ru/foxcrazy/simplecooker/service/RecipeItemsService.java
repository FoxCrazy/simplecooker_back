package ru.foxcrazy.simplecooker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.foxcrazy.simplecooker.domain.RecipeItems;
import ru.foxcrazy.simplecooker.repository.RecipeItemsRepo;

@Service
public class RecipeItemsService {
    @Autowired
    RecipeItemsRepo repo;
}
