import React, { Component } from "react";
import RecipeDataService from "../services/recipe.service";
import { Link } from "react-router-dom";
import AuthService from "../services/auth.service";


export default class RecipesList extends Component {
  constructor(props) {
    super(props);
    this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);
    this.retrieveRecipes = this.retrieveRecipes.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.setActiveRecipe = this.setActiveRecipe.bind(this);
    this.removeAllRecipes = this.removeAllRecipes.bind(this);
    this.searchTitle = this.searchTitle.bind(this);

    this.state = {
      currentUser: undefined,
      recipes: [],
      currentRecipe: null,
      currentIndex: -1,
      searchTitle: "",
      currentIngredients: [],
    };
  }

  componentDidMount() {
    this.retrieveRecipes();
    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }
  }

  onChangeSearchTitle(e) {
    const searchTitle = e.target.value;

    this.setState({
      searchTitle: searchTitle,
    });
  }

  retrieveRecipes() {
    RecipeDataService.getAll()
      .then((response) => {
        this.setState({
          recipes: response.data,
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  refreshList() {
    this.retrieveRecipes();
    this.setState({
      currentRecipe: null,
      currentIndex: -1,
    });
  }

  setActiveRecipe(recipe, index) {
    //here

    this.setState({
      currentRecipe: recipe,
      //currentIngredients: RecipeDataService.findIngsByRecipeId(recipe.id),
      currentIndex: index,
    });

    RecipeDataService.findIngsByRecipeId(recipe.recipeId)
      .then((response) => {
        this.setState({
          currentIngredients: response.data,
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  removeAllRecipes() {
    RecipeDataService.deleteAll()
      .then((response) => {
        console.log(response.data);
        this.refreshList();
      })
      .catch((e) => {
        console.log(e);
      });
  }

  deleteRecipe(recipe){
    RecipeDataService.deleteRecipe(recipe.recipeId);

    this.state.recipes.indexOf(recipe) !== -1
      ? this.state.recipes.splice(this.state.recipes.indexOf(recipe), 1)
      : console.log("unable to delete " + recipe.name);

      this.setState({recipes:this.state.recipes, currentRecipe:null,})

  }

  searchTitle() {
    RecipeDataService.findAllByTitle(this.state.searchTitle)
      .then((response) => {
        this.setState({
          recipes: response.data,
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    const {
      searchTitle,
      recipes,
      currentRecipe,
      currentIndex,
      currentIngredients,
      currentUser,
    } = this.state;

    return (
      <div>
        {currentUser ? (
          <div className="list row">
            <div className="col-md-10">
              <div className="input-group mb-3">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Search by title"
                  value={searchTitle}
                  onChange={this.onChangeSearchTitle}
                  onKeyPress={(ev) => {
                    //console.log(`Pressed ${ev.key}`);
                    if (ev.key === 'Enter') {
                      this.searchTitle();
                      ev.preventDefault();
                    }
                  }}
                />
                <div className="input-group-append">
                  <button
                    className="btn btn-outline-success"
                    type="button"
                    onClick={this.searchTitle}
                  >
                    Search
                  </button>
                </div>
              </div>
            </div>
            <div className="col-md-5">
              <h4>Recipes List <Link to={"/add/"} className="badge badge-success badge-margin float-right"  >
                        Add
                      </Link></h4>

              <ul className="list-group">
                {recipes &&
                  recipes.map((recipe, index) => (
                    <li
                      className={
                        "list-group-item " +
                        (index === currentIndex ? "active" : "")
                      }
                      onClick={() => {
                        console.log(recipe);
                        this.setActiveRecipe(recipe, index);
                      }}
                      key={index}
                    >
                      {recipe.name}
                    </li>
                  ))}
              </ul>

            </div>
            <div id="currecipe" className="col-md-7 ">
              {currentRecipe ? (
                <div>
                  <strong>
                    <h4>
                      {currentRecipe.name}

                      <Link
                        to={"/recipes/" + currentRecipe.recipeId}
                        className="badge badge-warning badge-margin"
                      >
                        Edit
                      </Link>
                      <Link to={"/recipes/"} className="badge badge-danger badge-margin" onClick={() => {
                        console.log(currentRecipe);
                        this.deleteRecipe(currentRecipe);
                      }}>
                        Delete
                      </Link>
                    </h4>
                  </strong>
                  <div>{currentRecipe.description}</div>

                  <div>
                    <label>
                      <strong>Ingredients:</strong>
                    </label>{" "}
                    <ol
                      id="ingListRecipe"
                      className="list-group-flush ing-list "
                    >
                      {currentIngredients &&
                        currentIngredients.map((currentIngredient, index) => (
                          <li
                            className={"list-group-item ing-list"}
                            key={index}
                          >
                            {currentIngredient.ingredientName}
                            <span className="badge float-right badge-success">
                              Grammar: {currentIngredient.grammar}
                            </span>
                            <span className="badge float-right badge-danger">
                              Calories:{" "}
                              {parseFloat(
                                (currentIngredient.grammar / 100) *
                                  currentIngredient.calories
                              ).toFixed(1)}
                            </span>
                          </li>
                        ))}
                    </ol>
                  </div>
                  <div>
                    <label>
                      <strong>Actions:</strong>
                    </label>{" "}
                    {currentRecipe.actions}
                  </div>

                </div>
              ) : (
                <div>
                  <br />
                  <p>Please click on a Recipe...</p>
                </div>
              )}
            </div>
          </div>
        ) : (
          <div className="card card-container">
            <div className="alert alert-danger" role="alert">
              You must login to view content
            </div>
            <Link
                to={"/login"}
                className="btn btn-success choose"
              >
                Login page
              </Link>
          </div>
        )}
      </div>
    );
  }
}
