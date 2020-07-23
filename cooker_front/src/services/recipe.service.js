import http from "../http-common";
import authHeader from './data.service';

class RecipeDataService {
  getAll() {
    return http.get("/recipe/main",{ headers: authHeader() });
  }

  get(id) {
    return http.get(`/recipe/${id}`,{ headers: authHeader() });
  }

  create(data) {
    return http.post("/recipe/create", data,{ headers: authHeader() });
  }
  createIng(data) {
    return http.post("/ingredient/create", data,{ headers: authHeader() });
  }

  update(id, data) {
    return http.put(`/recipe/${id}`, data,{ headers: authHeader() });
  }

  addIngs(id,data){
    return http.post(`/items/setingredients/${id}`,data,{ headers: authHeader() })
  }
  updateIngs(id,data){
    return http.put(`/items/setingredients/${id}`,data,{ headers: authHeader() })
  }
  
  deleteRecipe(id) {
    return http.delete(`/recipe/delete/${id}`,{ headers: authHeader() });
  }


  findAllByTitle(title) {
    // console.log(http.get(`/recipe/name/${title}`));
    return http.get(`/recipe/name/${title}`,{ headers: authHeader() });
  }
  findIngsByName(name){
    return http.get(`/ingredient/name/${name}`,{ headers: authHeader() })
  }

  findIngsByRecipeId(recipeId) {
    // console.log(http.get(`/recipe/name/${title}`));
    return http.get(`/items/forrecipe/${recipeId}`,{ headers: authHeader() });
  }

  getIngredientsList() {
    return http.get("/ingredient/main",{ headers: authHeader() });
  }
  deleteIngredient(id) {
    return http.delete(`/ingredient/delete/${id}`,{ headers: authHeader() });
  }
  findAllByIngredientTitle(title) {
    
    return http.get(`/ingredient/name/${title}`,{ headers: authHeader() });
  }
}

export default new RecipeDataService();