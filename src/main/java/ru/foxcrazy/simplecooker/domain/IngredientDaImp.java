package ru.foxcrazy.simplecooker.domain;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import ru.foxcrazy.simplecooker.domain.Ingredient;
@Repository
public class IngredientDaImp implements IngredientDa {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Ingredient> get() {
        Session currSession = entityManager.unwrap(Session.class);
        Query<Ingredient> query = currSession.createQuery( "from Ingredient", Ingredient.class);
        List<Ingredient> list = query.getResultList();
        return list;
    }
    @Override
    public Ingredient get(int id) {
        Session currSession = entityManager.unwrap(Session.class);
        Ingredient ingr = currSession.get(Ingredient.class, id);
        return ingr;
    }
    @Override
    public void save(Ingredient ingredient) {

        Session currSession = entityManager.unwrap(Session.class);
        currSession.saveOrUpdate(ingredient);
    }
    @Override
    public void delete(int id) {
        Session currSession = entityManager.unwrap(Session.class);
        Ingredient ingr = currSession.get(Ingredient.class, id);
        currSession.delete(ingr);
    }
}
