package ru.foxcrazy.simplecooker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.foxcrazy.simplecooker.repository.IngredientRepo;

@Service
public class IngredientService {
    @Autowired
    IngredientRepo repo;

}
