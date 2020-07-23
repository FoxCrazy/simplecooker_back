import React, { Component } from "react";
import RecipeDataService from "../services/recipe.service";

export default class Recipe extends Component {
  constructor(props) {
    super(props);
    this.onChangeTitle = this.onChangeTitle.bind(this);
    this.onChangeDescription = this.onChangeDescription.bind(this);
    this.onChangeActions=this.onChangeActions.bind(this);
    this.getRecipe = this.getRecipe.bind(this);
    this.updateRecipe = this.updateRecipe.bind(this);
    this.searchIngs = this.searchIngs.bind(this);
    this.onChangeAddedIngs = this.onChangeAddedIngs.bind(this);
    this.onChangeSearchIngs = this.onChangeSearchIngs.bind(this);

    this.state = {
      currentRecipe: {
        id: null,
        name: "",
        description: "",
        ingredients: null,
        currentRecipe:null,
        searchIngs:"",
      },
      message: ""
    };
  }

  componentDidMount() {
    this.getRecipe(this.props.match.params.id);
  }

  onChangeTitle(e) {
    const title = e.target.value;

    this.setState(function(prevState) {
      return {
        currentRecipe: {
          ...prevState.currentRecipe,
          name: title
        }
      };
    });
  }

  onChangeDescription(e) {
    const description = e.target.value;
    
    this.setState(prevState => ({
      currentRecipe: {
        ...prevState.currentRecipe,
        description: description
      }
    }));
  }
  onChangeActions(e) {
    const actions = e.target.value;
    
    this.setState(prevState => ({
      currentRecipe: {
        ...prevState.currentRecipe,
        actions: actions
      }
    }));
  }

  getRecipe(id) {
    RecipeDataService.get(id)
      .then(response => {
        this.setState({
          currentRecipe: response.data
        });
        this.retrieveIngredients(this.state.currentRecipe.recipeId);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
      
  }

  retrieveIngredients(recipeId) {
    RecipeDataService.findIngsByRecipeId(recipeId)
    .then((response) => {
      this.setState({
        addedIngs: response.data,
      });
      console.log(response.data);
    })
    .catch((e) => {
      console.log(e);
    });
  }

  onChangeSearchIngs(e) {
    const searchIngs = e.target.value;

    this.setState({
      searchIngs: searchIngs,
    });
  }
  onChangeAddedIngs(e) {
    const addedIngs = e.target.value;

    this.setState({
      addedIngs: addedIngs,
    });
  }
  onChangeGrams(id) {
    return (e) => {
      const changedIngs = this.state.addedIngs.map((i) =>
        i.id === id ? { ...i, grammar: parseInt(e.target.value) } : i
      );
      console.log(changedIngs);
      this.setState({
        addedIngs: changedIngs,
      });
    };
  }

  updateRecipe() {
    RecipeDataService.update(
      this.state.currentRecipe.recipeId,
      this.state.currentRecipe
    )
      .then(response => {
        console.log(response.data);
        this.setState({
          message: "The Recipe was updated successfully!"
        });
        RecipeDataService.updateIngs(
          this.state.currentRecipe.recipeId,
          JSON.stringify(this.state.addedIngs),
        );
      })
      .catch(e => {
        console.log(e);
      });
  }
  setActiveIngredient(ingredient) {
    this.state.addedIngs.indexOf(ingredient) === -1
      ? this.state.addedIngs.push(ingredient)
      : console.log(ingredient.ingredientName + " is already added");

    console.log(this.state.addedIngs);
    this.setState({ addedIngs: this.state.addedIngs });
  }
  setDisableIngredient(ingredient) {
    this.state.addedIngs.indexOf(ingredient) !== -1
      ? this.state.addedIngs.splice(this.state.addedIngs.indexOf(ingredient), 1)
      : console.log("unable to delete " + ingredient.ingredientName);

    console.log(this.state.addedIngs);
    this.setState({ addedIngs: this.state.addedIngs });
  }
  searchIngs() {
    //console.log(this.state.searchIngs);
    RecipeDataService.findIngsByName(this.state.searchIngs)
      .then((response) => {
        this.setState({
          ingredients: response.data.map((i) => ({ ...i, grammar: 0 })),
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    const { searchIngs, ingredients, addedIngs, currentIndex, currentRecipe } = this.state;

    return (
      <div>
        {currentRecipe ? (
          <div className="edit-form">
            <h4>Recipe</h4>
            <form>
              <div className="form-group">
                <label htmlFor="title">Title</label>
                <input
                  type="text"
                  className="form-control"
                  id="title"
                  value={currentRecipe.name}
                  onChange={this.onChangeTitle}
                />
              </div>
              <div className="form-group">
                <label htmlFor="description">Description</label>
                <input
                  type="text"
                  className="form-control"
                  id="description"
                  value={currentRecipe.description}
                  onChange={this.onChangeDescription}
                />
              </div>
              <div className="form-group">
                <label htmlFor="actions">Actions</label>
                <textarea
                  type="text"
                  className="form-control"
                  id="actions"
                  value={currentRecipe.actions}
                  onChange={this.onChangeActions}
                  rows="7"
                />
              </div>
              <ul id="addedList" className="list-group searched-ings">
                {addedIngs &&
                  addedIngs.map((ingredient, id) => (
                    <li
                      className={
                        "list-group-item list-group-item-warning" +
                        (id === currentIndex ? "active" : "")
                      }
                      onClick={() => {
                        //console.log(ingredient);
                        //this.setDisableIngredient(ingredient);
                      }}
                      key={id}
                    >
                      {" "}
                      <span className="badge float-right badge-danger" onClick={() => {
                        console.log(ingredient);
                        this.setDisableIngredient(ingredient);
                      }}>
                      &#8212;
                      </span>
                      <span className="badge float-right badge-success">
                        {ingredient.calories} calories per 100g
                      </span>
                      {ingredient.ingredientName}
                      <input
                        placeholder="grams"
                        type="number"
                        min="1"
                        value={ingredient.grammar}
                        className="form-control form-control-sm calories float-right"
                        requiredkey={id}
                        onChange={this.onChangeGrams(ingredient.id)}
                      />
                    </li>
                  ))}
              </ul>
              <h6>Add more with search</h6>
              <div className="input-group ">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Search ingredients"
                  defaultValue={searchIngs}
                  onChange={this.onChangeSearchIngs}
                  onKeyPress={(ev) => {
                    //console.log(`Pressed ${ev.key}`);
                    if (ev.key === 'Enter') {
                      this.searchIngs();
                      ev.preventDefault();
                    }
                  }}
                />
                <div className="input-group-append">
                  <button
                    className="btn btn-outline-success"
                    type="button"
                    onClick={this.searchIngs}
                  >
                    Search
                  </button>
                </div>
              </div>
              <ul className="list-group">
                {ingredients &&
                  ingredients.map((ingredient, index) => (
                    <li
                      className={
                        "list-group-item list-group-item-action" +
                        (index === currentIndex ? "active" : "")
                      }
                      onClick={() => {
                        console.log(ingredient);
                        this.setActiveIngredient(ingredient);
                      }}
                      key={index}
                    >
                      <span className="badge float-right badge-success">
                        {ingredient.calories} calories per 100g
                      </span>
                      {ingredient.ingredientName}
                    </li>
                  ))}
              </ul>

              
            </form>

         
            <button onClick={this.updateRecipe} className="btn btn-success">
              Update recipe
            </button>

          </div>
        ) : (
          <div>
            <br />
            <p>Please click on a Recipe...</p>
          </div>
        )}
      </div>
    );
  }
}
