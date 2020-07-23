import React, { Component } from "react";
import RecipeDataService from "../services/recipe.service";
import { Link } from "react-router-dom";
import AuthService from "../services/auth.service";


export default class IngredientsList extends Component {
  constructor(props) {
    super(props);
    this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);
    this.retrieveIngredients = this.retrieveIngredients.bind(this);
    this.setActiveIngredient = this.setActiveIngredient.bind(this);
    this.searchTitleIngredient = this.searchTitleIngredient.bind(this);

    this.state = {
      currentUser: undefined,
      ingredients: [],
      currentIngredient: null,
      currentIndex: -1,
      searchTitleIngredient: "",
    };
  }

  componentDidMount() {
    this.retrieveIngredients();
    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }
  }

  onChangeSearchTitle(e) {
    const searchTitle = e.target.value;
    console.log(searchTitle)
    this.setState({
      searchTitleIngredient: searchTitle,
    });
  }

  retrieveIngredients() {
    RecipeDataService.getIngredientsList()
      .then((response) => {
        this.setState({
          ingredients: response.data,
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }


  setActiveIngredient(ingredient, index) {
    //here

    this.setState({
      currentIngredient: ingredient,
      currentIndex: index,
    });

    
  }



  deleteIngredient(ingredient){
    RecipeDataService.deleteIngredient(ingredient.id);

    this.state.ingredients.indexOf(ingredient) !== -1
      ? this.state.ingredients.splice(this.state.ingredients.indexOf(ingredient), 1)
      : console.log("unable to delete " + ingredient.ingredientName);

      this.setState({recipes:this.state.ingredients, currentIngredient:null,})

  }

  searchTitleIngredient() {
    RecipeDataService.findAllByIngredientTitle(this.state.searchTitleIngredient)
      .then((response) => {
        this.setState({
          ingredients: response.data,
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
      ingredients,
      currentIngredient,
      currentIndex,
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
                      this.searchTitleIngredient();
                      ev.preventDefault();
                    }
                  }}
                />
                <div className="input-group-append">
                  <button
                    className="btn btn-outline-success"
                    type="button"
                    onClick={this.searchTitleIngredient}
                  >
                    Search
                  </button>
                </div>
              </div>
            </div>
            <div className="col-md-5">
              <h4>Ingredients List <Link to={"/addIng/"} className="badge badge-success badge-margin float-right"  >
                        Add
                      </Link></h4>

              <ul className="list-group">
                {ingredients &&
                  ingredients.map((ingredient, index) => (
                    <li
                      className={
                        "list-group-item " +
                        (index === currentIndex ? "active" : "")
                      }
                      onClick={() => {
                        console.log(ingredient);
                        this.setActiveIngredient(ingredient, index);
                      }}
                      key={index}
                    >
                      {ingredient.ingredientName}
                    </li>
                  ))}
              </ul>

            </div>
            <div id="currecipe" className="col-md-7 ">
              {currentIngredient ? (
                <div>
                  <strong>
                    <h4>
                      {currentIngredient.ingredientName}

                    
                      <Link to={"/ingredients/"} className="badge badge-danger badge-margin" onClick={() => {
                        console.log(currentIngredient);
                        this.deleteIngredient(currentIngredient);
                      }}>
                        Delete
                      </Link>
                    </h4>
                  </strong>
                  

                  
                  <div>
                    <label>
                      <strong>Calories per 100 gram:</strong>
                    </label>{" "}
                    {currentIngredient.calories}
                  </div>

                </div>
              ) : (
                <div>
                  <br />
                  <p>Please click on an Ingredient...</p>
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
